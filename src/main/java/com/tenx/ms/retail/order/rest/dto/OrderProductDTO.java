package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class OrderProductDTO {

    @ApiModelProperty("product id")
    @NotNull
    private Long productId;

    @ApiModelProperty("Product count")
    @NotNull
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public OrderProductDTO setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public OrderProductDTO setCount(Long count) {
        this.count = count;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OrderProductDTO that = (OrderProductDTO) o;

        return productId != null ? productId.equals(that.productId) : that.productId == null;
    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }
}
