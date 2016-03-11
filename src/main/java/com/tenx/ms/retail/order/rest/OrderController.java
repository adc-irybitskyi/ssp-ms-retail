package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.service.OrderService;
import com.tenx.ms.retail.order.util.OrderStatus;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "orders", description = "Order Entity Management API")
@RestController("OrderControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Add/Update Product Quantity")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of Order details"),
        @ApiResponse(code = 404, message = "Invalid store_id or product_id"),
        @ApiResponse(code = 412, message = "Precondition failure"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    public OrderDTO addOrder(
        @ApiParam(name = "storeId", value = "The id of the store to get its details") @PathVariable() long storeId,
        @Validated @RequestBody OrderDTO order) {

        LOGGER.debug("Update Order: {}", order);

        return orderService.addOrder(order
            .setStoreId(storeId)
            .setStatus(OrderStatus.ORDERED));
    }
}
