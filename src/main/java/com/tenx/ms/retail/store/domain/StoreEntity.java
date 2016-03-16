package com.tenx.ms.retail.store.domain;

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
@Table(name = "stores")
public class StoreEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(max = 50)
    private String name;

    public Long getStoreId() {
        return storeId;
    }

    public StoreEntity setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public StoreEntity setName(String name) {
        this.name = name;
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

        StoreEntity that = (StoreEntity) o;

        return storeId != null ? storeId.equals(that.storeId) : that.storeId == null;
    }

    @Override
    public int hashCode() {
        return storeId != null ? storeId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
            "storeId=" + storeId +
            ", name='" + name + '\'' +
            '}';
    }
}
