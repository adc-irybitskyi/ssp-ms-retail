package com.tenx.ms.retail.product.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @NotNull
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @NotNull
    @Column(name = "sku", length = 10)
    @Size(min = 5, max = 10)
    private String sku;

    @NotNull
    @Column(name = "price")
    private Double price;

    public Long getStoreId() {
        return storeId;
    }

    public ProductEntity setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductEntity that = (ProductEntity) o;

        return storeId.equals(that.storeId);
    }

    @Override
    public int hashCode() {
        return storeId.hashCode();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
            "productId=" + productId +
            ", storeId=" + storeId +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", sku='" + sku + '\'' +
            ", price=" + price +
            '}';
    }
}
