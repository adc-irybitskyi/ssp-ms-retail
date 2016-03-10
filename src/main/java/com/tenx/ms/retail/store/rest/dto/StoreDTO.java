package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class StoreDTO {
    @ApiModelProperty("The store ID - (readonly)")
    private Long storeId;

    @ApiModelProperty("Name of the store")
    @NotEmpty
    @Length(max = 50)
    private String name;

    public Long getStoreId() {
        return storeId;
    }

    public StoreDTO setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StoreDTO setName(String name) {
        this.name = name;
        return this;
    }
}
