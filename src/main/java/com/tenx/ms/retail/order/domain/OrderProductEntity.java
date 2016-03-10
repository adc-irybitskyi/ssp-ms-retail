package com.tenx.ms.retail.order.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "order_products")
@IdClass(OrderProductPK.class)
public class OrderProductEntity implements Serializable {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @NotNull
    @Column(name = "product_count")
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public OrderProductEntity setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCount() {
        return count;
    }

    public OrderProductEntity setCount(Long count) {
        this.count = count;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OrderProductEntity that = (OrderProductEntity) o;

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
