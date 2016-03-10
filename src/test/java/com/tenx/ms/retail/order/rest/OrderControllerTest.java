package com.tenx.ms.retail.order.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
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
public class OrderControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/orders/%s/";

    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:orderTests/add-order-1-request.json")
    private File addOrder1Request;

    @Test
    public void addOrder() throws IOException {
        Long storeId = 1L;
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), storeId),
            FileUtils.readFileToString(addOrder1Request),
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

        String received = response.getBody();

        OrderDTO order = mapper.readValue(received, OrderDTO.class);
        assertThat(order, is(notNullValue()));
        assertThat(order.getStoreId(), is(storeId));
        assertThat(order.getStatus(), is(1));//TODO: use Order Status Enum
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
