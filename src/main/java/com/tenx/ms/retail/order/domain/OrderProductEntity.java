package com.tenx.ms.retail.order.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "order_products",
    uniqueConstraints = @UniqueConstraint(columnNames = { "order_id", "product_id" }))
public class OrderProductEntity implements Serializable {

    @Id
    @Column(name = "order_product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @NotNull
    private OrderEntity order;

    @NotNull
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

    public OrderEntity getOrder() {
        return order;
    }

    public OrderProductEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
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

        if (order != null ? !order.equals(that.order) : that.order != null)
            return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
