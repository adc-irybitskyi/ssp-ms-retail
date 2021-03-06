package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;
import com.tenx.ms.retail.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "stores", description = "Store Entity Management API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Add a Store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of store details"),
        @ApiResponse(code = 412, message = "Invalid StoreEntity"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated addStore(@Validated @RequestBody
    StoreDTO store) {

        LOGGER.debug("Add new store: {}", store);

        return new ResourceCreated<>(storeService.addStore(store));
    }

//TODO:    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "Finds all stores", authorizations = { @Authorization("ROLE_ADMIN")})
    @ApiOperation(value = "Finds all stores")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of stores"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.GET)
    public Paginated<StoreDTO> getAllStores(Pageable pageable) {

        LOGGER.debug("Fetching all stores: {}", pageable);

        return storeService.getAllStores(pageable, RestConstants.VERSION_ONE + "/stores");
    }

    @ApiOperation(value = "Find store by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of store"),
        @ApiResponse(code = 404, message = "Store can't be found by id"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public StoreDTO getStoreById(
        @ApiParam(name = "storeId", value = "The id of the store to get its details")
        @PathVariable() long storeId) {

        LOGGER.debug("Fetching store by store id: {}", storeId);

        return storeService.getStoreById(storeId).get();
    }

    @ApiOperation(value = "Find store by name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of store"),
        @ApiResponse(code = 404, message = "Store can't be found by name"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = {"/{storeName:.*[a-zA-Z].*}"}, method = RequestMethod.GET)
    public StoreDTO getStoreByName(
        @ApiParam(name = "storeName", value = "The name of the store to get its details")
        @PathVariable String storeName) {

        LOGGER.debug("Fetching store by store name: {}", storeName);

        return storeService.getStoreByName(storeName).get();
    }
}
