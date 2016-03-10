package com.tenx.ms.retail.product.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.ProductDTO;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class ProductControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/products/";

    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:productTests/add-product-1-request.json")
    private File addProduct1Request;

    @Test
    public void happyPath() {
        Integer productId;
        try {
            //add Product
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    FileUtils.readFileToString(addProduct1Request),
                    HttpMethod.POST);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                ResourceCreated rc = mapper.readValue(received, ResourceCreated.class);
                productId = (Integer) rc.getId();
                assertThat(productId > 0, is(true));
            }
            //get All
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    null,
                    HttpMethod.GET);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                Paginated<ProductDTO> productPaginated = mapper.readValue(received, new TypeReference<Paginated<ProductDTO>>(){});
                assertThat(productPaginated.getTotalCount(), is(1L));
                assertThat(productPaginated.getContent().get(0).getName(), is("product-1"));
            }
            //get Product by Id
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + productId,
                    null,
                    HttpMethod.GET);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                //Paginated<Product> list = mapper.readValue(received, Paginated.class);
                ProductDTO product = mapper.readValue(received, ProductDTO.class);
                assertThat(product, is(notNullValue()));
                assertThat(product.getName(), is("product-1"));
            }
            //get Product by Name
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + "product-1",
                    null,
                    HttpMethod.GET);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                //Paginated<Product> list = mapper.readValue(received, Paginated.class);
                ProductDTO product = mapper.readValue(received, ProductDTO.class);
                assertThat(product, is(notNullValue()));
                assertThat(product.getProductId(), is(productId.longValue()));
                assertThat(product.getName(), is("product-1"));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidAddProductRequest(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()),
            "{}",
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    public void unknownProductId(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()) + "/" + "111/",
            null,
            HttpMethod.GET);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void unknownProductName(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()) + "/" + "unknownName",
            null,
            HttpMethod.GET);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
