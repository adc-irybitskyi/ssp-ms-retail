package com.tenx.ms.retail.stock.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "stocks")
@IdClass(StockPK.class)
public class StockEntity implements Serializable {
    //No identifier specified for entity: com.tenx.ms.retail.stock.domain.StockEntity

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "available_count")
    private Long count;

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

        if (productId != null ? !productId.equals(that.productId) : that.productId != null)
            return false;
        return storeId != null ? storeId.equals(that.storeId) : that.storeId == null;

    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockEntity{" +
            "productId=" + productId +
            ", storeId=" + storeId +
            ", count=" + count +
            '}';
    }
}
