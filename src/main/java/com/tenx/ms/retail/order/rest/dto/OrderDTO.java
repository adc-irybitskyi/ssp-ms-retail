package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.order.util.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public class OrderDTO {

    @ApiModelProperty("Readonly - assigned order id")
    private Long orderId;

    @ApiModelProperty("Readonly - store associated with this product")
    private Long storeId;

    @ApiModelProperty("date/time of the order")
    private Date orderDate;

    @ApiModelProperty("read only (output should be in label form)")
    private OrderStatus status;

    @ApiModelProperty("one or more products purchased")
    @NotNull
    private Set<OrderProductDTO> products;

    @ApiModelProperty("the purchaser first name (alpha only)")
    @NotNull
    @Size(max = 50)
    //TODO: Add (alpha only) validation
    private String firstName;

    @ApiModelProperty("the purchaser last name (alpha only)")
    @NotNull
    @Size(max = 50)
    //TODO: Add (alpha only) validation
    private String lastName;

    @ApiModelProperty("the purchaser email")
    @Email
    @Size(max = 255)
    private String email;

    @PhoneNumber
    @ApiModelProperty("the purchaser phone number")
    @Size(max = 10)
    private String phone;

    public Long getOrderId() {
        return orderId;
    }

    public OrderDTO setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderDTO setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderDTO setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Set<OrderProductDTO> getProducts() {
        return products;
    }

    public OrderDTO setProducts(Set<OrderProductDTO> products) {
        this.products = products;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public OrderDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OrderDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OrderDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Long getStoreId() {
        return storeId;
    }

    public OrderDTO setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

}
