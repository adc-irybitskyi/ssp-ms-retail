package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.service.StoreService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiOperation(value = "Get store details by Id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of store details"),
        @ApiResponse(code = 412, message = "Invalid StoreEntity"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated addStore(@RequestBody Store store) {

        LOGGER.debug("Add new store: {}", store);

        return new ResourceCreated<Long>(storeService.addStore(store));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Finds all stores", authorizations = { @Authorization("ROLE_ADMIN")})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of stores"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.GET)
    public Paginated<Store> getAllStores(Pageable pageable) {

        LOGGER.debug("Fetching all stores: {}", pageable);

        return storeService.getAllStores(pageable, RestConstants.VERSION_ONE + "/stores");
    }
}
