package com.tenx.ms.retail.product.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ProductDTO {

    @ApiModelProperty("The product ID - (readonly)")
    private Long productId;

    @ApiModelProperty("The store ID - (readonly)")
    private Long storeId;

    @ApiModelProperty("Name of the product")
    @NotEmpty
    @Length(max = 50)
    private String name;

    @ApiModelProperty("description of the item")
    @Length(max = 150)
    private String description;

    //TODO: an adhoc SKU allowing only alpha-numeric with a min length of 5 and max of 10
    @ApiModelProperty("an adhoc SKU allowing only alpha-numeric with a min length of 5 and max of 10")
    @NotEmpty
    private String sku;

    //TODO: Must allow for XXX.XX format
    @ApiModelProperty("The price of this product")
    @NotNull
    private Double price;

    public Long getProductId() {
        return productId;
    }

    public ProductDTO setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getStoreId() {
        return storeId;
    }

    public ProductDTO setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductDTO setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductDTO setPrice(Double price) {
        this.price = price;
        return this;
    }
}
