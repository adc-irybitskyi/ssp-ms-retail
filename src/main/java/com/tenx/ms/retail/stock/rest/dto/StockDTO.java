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

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
