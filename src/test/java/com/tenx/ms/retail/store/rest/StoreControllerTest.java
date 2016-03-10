package com.tenx.ms.retail.store.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StoreControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/stores/";
    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:storeTests/add-store-1-request.json")
    private File addStore1Request;

    @Test
    public void happyPath() {
        Integer storeId;
        try {
            //add Store
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    FileUtils.readFileToString(addStore1Request),
                    HttpMethod.POST);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                ResourceCreated rc = mapper.readValue(received, ResourceCreated.class);
                storeId = (Integer) rc.getId();
                assertThat(storeId > 0, is(true));
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

                Paginated<StoreDTO> storePaginated = mapper.readValue(received, new TypeReference<Paginated<StoreDTO>>(){});
                assertThat(storePaginated.getTotalCount(), is(1L));
                assertThat(storePaginated.getContent().get(0).getName(), is("store-1"));
            }
            //get Store by Id
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId,
                    null,
                    HttpMethod.GET);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                StoreDTO store = mapper.readValue(received, StoreDTO.class);
                assertThat(store, is(notNullValue()));
                assertThat(store.getName(), is("store-1"));
            }
            //get Store by Name
            {
                ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + "store-1",
                    null,
                    HttpMethod.GET);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                StoreDTO store = mapper.readValue(received, StoreDTO.class);
                assertThat(store, is(notNullValue()));
                assertThat(store.getStoreId(), is(storeId.longValue()));
                assertThat(store.getName(), is("store-1"));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidAddStoreRequest(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()),
            "{}",
            HttpMethod.POST);

        assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    public void unknownStoreId(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()) + "/" + "111/",
            null,
            HttpMethod.GET);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void unknownStoreName(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()) + "/" + "unknownName",
            null,
            HttpMethod.GET);

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
