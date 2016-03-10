package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import com.tenx.ms.retail.stock.service.StockService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "stocks", description = "Stock Entity Management API")
@RestController("StockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stocks")
public class StockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Add/Update Product Quantity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of Stock details"),
        @ApiResponse(code = 412, message = "Invalid StockEntity"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public void updateStock(@Validated @RequestBody StockDTO Stock) {

        LOGGER.debug("Update Stock: {}", Stock);

        stockService.updateStock(Stock);
    }
}
