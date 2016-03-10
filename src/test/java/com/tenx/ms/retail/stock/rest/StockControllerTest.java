package com.tenx.ms.retail.stock.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
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
public class StockControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/stocks/";

    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:stockTests/add-stock-1-request.json")
    private File addStock1Request;

    @Value("classpath:stockTests/update-stock-2-request.json")
    private File updateStock2Request;

    @Test
    public void happyPath() {
        Integer stockId;
        try {
            //add Stock
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    FileUtils.readFileToString(addStock1Request),
                    HttpMethod.POST);

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            }
            //update Stock
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    FileUtils.readFileToString(updateStock2Request),
                    HttpMethod.POST);

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidAddStockRequest(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()),
            "{}",
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

}
