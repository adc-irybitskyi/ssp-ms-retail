package com.tenx.ms.retail.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {
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
    //an adhoc SKU allowing only alpha-numeric with a min length of 5 and max of 10
    private String sku;

    @NotNull
    @Column(name = "price")
    //TODO: Must allow for XXX.XX format
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

    public Long getProductId() {
        return productId;
    }

    public ProductEntity setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductEntity setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductEntity setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntity that = (ProductEntity) o;

        return productId != null ? productId.equals(that.productId) : that.productId == null;
    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
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
