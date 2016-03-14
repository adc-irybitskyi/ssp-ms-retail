package com.tenx.ms.retail.order.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.rest.dto.OrderProductDTO;
import com.tenx.ms.retail.order.util.OrderStatus;
import com.tenx.ms.retail.product.rest.dto.ProductDTO;
import com.tenx.ms.retail.product.service.ProductService;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import com.tenx.ms.retail.stock.service.StockService;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;
import com.tenx.ms.retail.store.service.StoreService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class OrderControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/orders/%s/";

    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @Value("classpath:orderTests/add-order-1-request.json")
    private File addOrder1Request;

    @Value("classpath:orderTests/invalid-product-id-request.json")
    private File invalidProductIdRequest;

    @Test
    public void addOrder() throws IOException {
        // Pre Conditions
        Long storeId = storeService.addStore(new StoreDTO().setName("OrderControllerTest-Store"));

        Long productId1 = productService.addProduct(new ProductDTO()
            .setStoreId(storeId)
            .setName("OrderControllerTest-Product1")
            .setSku("111111")
            .setPrice(100.00));

        Long productId2 = productService.addProduct(new ProductDTO()
            .setStoreId(storeId)
            .setName("OrderControllerTest-Product2")
            .setSku("122222")
            .setPrice(200.00));

        stockService.updateStock(new StockDTO()
            .setStoreId(storeId)
            .setProductId(productId1)
            .setCount(1L));

        stockService.updateStock(new StockDTO()
            .setStoreId(storeId)
            .setProductId(productId2)
            .setCount(2L));

        // Add Order - Invalid Phone Number
        {
            ResponseEntity<String> response = getJSONResponse(template,
                String.format(REQUEST_URI, basePath(),
                    storeId),
                mapper.writeValueAsString(new OrderDTO()
                    .setProducts(Collections.singleton(new OrderProductDTO()
                        .setProductId(productId1)
                        .setCount(1L)))
                    .setFirstName("firstNameValue")
                    .setLastName("lastNameValue")
                    .setEmail("email@tenx.com")
                    .setPhone("12345")),
                HttpMethod.POST);

            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        }
        // Add Order
        {
            Set<OrderProductDTO> products = new HashSet<>();
            products.add(new OrderProductDTO().setProductId(productId1).setCount(1L));
            products.add(new OrderProductDTO().setProductId(productId2).setCount(2L));

            ResponseEntity<String> response = getJSONResponse(template,
                String.format(REQUEST_URI, basePath(),
                    storeId),
                mapper.writeValueAsString(new OrderDTO()
                    .setProducts(products)
                    .setFirstName("firstNameValue")
                    .setLastName("lastNameValue")
                    .setEmail("email@tenx.com")
                    .setPhone("1234567890")),
                HttpMethod.POST);

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            String received = response.getBody();

            OrderDTO order = mapper.readValue(received, OrderDTO.class);
            assertThat(order, is(notNullValue()));
            assertThat(order.getStoreId(), is(storeId));
            assertThat(order.getStatus(), is(OrderStatus.ORDERED));
            assertThat(order.getOrderDate(), is(notNullValue()));
            assertThat(order.getProducts(), is(notNullValue()));

            assertThat(order.getProducts().size(), is(2));
            assertThat(order.getProducts().contains(new OrderProductDTO().setProductId(productId1)), is(true));
            assertThat(order.getProducts().contains(new OrderProductDTO().setProductId(productId2)), is(true));

            order.getProducts().stream().filter(o -> o.getProductId().equals(productId1)).forEach(o -> assertThat(o.getCount(), is(1L)));
            order.getProducts().stream().filter(o -> o.getProductId().equals(productId2)).forEach(o -> assertThat(o.getCount(), is(2L)));
        }
        // Add again Order
        {
            Set<OrderProductDTO> products = new HashSet<>();
            products.add(new OrderProductDTO().setProductId(productId1).setCount(1L));
            products.add(new OrderProductDTO().setProductId(productId2).setCount(2L));

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath(), storeId),
                mapper.writeValueAsString(new OrderDTO().setProducts(products).setFirstName("firstNameValue").setLastName("lastNameValue").setEmail("email@tenx.com").setPhone("1234567890")),
                HttpMethod.POST);

            assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Test
    public void invalidStoreId() throws IOException {
        Long storeId = 0L;
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), storeId),
            FileUtils.readFileToString(addOrder1Request),
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void invalidProductId() throws IOException {
        Long storeId = 0L;
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), storeId),
            FileUtils.readFileToString(invalidProductIdRequest),
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void invalidAddOrderRequest(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), "1"),
            "{}",
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

}
