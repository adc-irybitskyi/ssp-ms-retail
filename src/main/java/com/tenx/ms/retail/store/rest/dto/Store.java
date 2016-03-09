package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModelProperty;

public class Store {
    @ApiModelProperty("The store ID - (readonly)")
    private Long storeId;

    @ApiModelProperty("Name of the store")
    private String name;

    public Long getStoreId() {
        return storeId;
    }

    public Store setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Store setName(String name) {
        this.name = name;
        return this;
    }
}
