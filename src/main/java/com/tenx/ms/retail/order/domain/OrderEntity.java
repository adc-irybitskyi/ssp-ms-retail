package com.tenx.ms.retail.order.domain;

import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.order.util.OrderStatus;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "order_date")
    private Date orderDate;

    @NotNull
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @OneToMany(mappedBy = "order")
    private Set<OrderProductEntity> products;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Email
    @Size(max = 255)
    @Column(length = 255)
    private String email;

    @PhoneNumber
    @Size(max = 10)
    @Column(length = 10)
    private String phone;

    public Long getOrderId() {
        return orderId;
    }

    public OrderEntity setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getStoreId() {
        return storeId;
    }

    public OrderEntity setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderEntity setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderEntity setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public OrderEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OrderEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OrderEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Set<OrderProductEntity> getProducts() {
        return products;
    }

    public OrderEntity setProducts(Set<OrderProductEntity> products) {
        this.products = products;
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

        OrderEntity entity = (OrderEntity) o;

        return orderId != null ? orderId.equals(entity.orderId) : entity.orderId == null;

    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
}
