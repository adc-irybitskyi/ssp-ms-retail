package com.tenx.ms.retail.order.domain;

import java.io.Serializable;

public class OrderProductPK implements Serializable {
    private Long orderId;

    private Long productId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OrderProductPK that = (OrderProductPK) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null)
            return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;

    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
