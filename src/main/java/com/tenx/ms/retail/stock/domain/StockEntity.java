package com.tenx.ms.retail.stock.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "stocks",
    uniqueConstraints = @UniqueConstraint(columnNames = { "stock_id", "product_id" }))
public class StockEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "available_count")
    private Long count;

    public Long getId() {
        return id;
    }

    public StockEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public StockEntity setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getStoreId() {
        return storeId;
    }

    public StockEntity setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public StockEntity setCount(Long count) {
        this.count = count;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StockEntity that = (StockEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
