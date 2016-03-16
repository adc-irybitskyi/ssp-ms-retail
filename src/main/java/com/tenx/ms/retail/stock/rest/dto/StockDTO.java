package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class StockDTO {

    @ApiModelProperty("Readonly - assigned product id")
    @NotNull
    private Long productId;

    @ApiModelProperty("Readonly - store associated with this product")
    @NotNull
    private Long storeId;

    @ApiModelProperty("Total count in stock")
    @NotNull
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public StockDTO setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getStoreId() {
        return storeId;
    }

    public StockDTO setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public StockDTO setCount(Long count) {
        this.count = count;
        return this;
    }
}
