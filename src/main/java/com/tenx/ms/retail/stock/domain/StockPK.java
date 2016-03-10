package com.tenx.ms.retail.stock.domain;

import java.io.Serializable;

public class StockPK implements Serializable {
    private Long productId;

    private Long storeId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StockPK stockId = (StockPK) o;

        if (productId != null ? !productId.equals(stockId.productId) : stockId.productId != null)
            return false;
        return storeId != null ? storeId.equals(stockId.storeId) : stockId.storeId == null;

    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        return result;
    }
}
