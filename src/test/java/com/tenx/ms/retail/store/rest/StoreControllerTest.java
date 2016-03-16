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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SuppressWarnings("PMD")
public class StoreControllerTest extends AbstractIntegrationTest {
    private static final String REQUEST_URI = "%s/v1/stores/";
    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:storeTests/add-store-1-request.json")
    private File addStore1Request;

    @Test
    public void addStore() {
        Integer storeId;
        try {
            {
                ResponseEntity<String> response = getJSONResponse(template,
                    String.format(REQUEST_URI, basePath()),
                    FileUtils.readFileToString(addStore1Request),
                    HttpMethod.POST);
                String received = response.getBody();

                assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                ResourceCreated rc = mapper.readValue(received, ResourceCreated.class);
                storeId = (Integer) rc.getId();
                assertThat(storeId > 0, is(true));
            }

            {
                try{
                    ResponseEntity<String> response = getJSONResponse(
                        template,
                        String.format(REQUEST_URI, basePath()) + storeId,
                        null,
                        HttpMethod.GET);
                    String received = response.getBody();

                    assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

                    StoreDTO store = mapper.readValue(received, StoreDTO.class);
                    assertThat(store, is(notNullValue()));
                    assertThat(store.getName(), is("StoreController-store-1"));
                } catch (IOException e) {
                    fail(e.getMessage());
                }

            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllStores() {
        try{
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()),
                null,
                HttpMethod.GET);
            String received = response.getBody();

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            Paginated<StoreDTO> storePaginated = mapper.readValue(received, new TypeReference<Paginated<StoreDTO>>(){});
            assertThat(storePaginated.getTotalCount(), is(greaterThan(1L)));
            List<StoreDTO> stores = storePaginated.getContent().stream().filter(s -> s.getStoreId() >= 1001 && s.getStoreId() <= 1002).collect(Collectors.toList());
            Collections.sort(stores, (o1, o2) -> o1.getStoreId() > o2.getStoreId() ? 1 : -1);
            assertThat(stores.size(), is(2));
            assertThat(stores.get(0).getStoreId(), is(1001L));
            assertThat(stores.get(0).getName(), is("global-store-1"));
            assertThat(stores.get(1).getName(), is("global-store-2"));
            assertThat(stores.get(1).getStoreId(), is(1002L));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getStoreById() {
        Long storeId = 1001L;
        try{
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()) + storeId,
                null,
                HttpMethod.GET);
            String received = response.getBody();

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            StoreDTO store = mapper.readValue(received, StoreDTO.class);
            assertThat(store, is(notNullValue()));
            assertThat(store.getName(), is("global-store-1"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getStoreByName() {
        try{
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath()) + "global-store-1",
            null,
            HttpMethod.GET);
        String received = response.getBody();

        assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

        StoreDTO store = mapper.readValue(received, StoreDTO.class);
        assertThat(store, is(notNullValue()));
        assertThat(store.getStoreId(), is(1001L));
        assertThat(store.getName(), is("global-store-1"));
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
