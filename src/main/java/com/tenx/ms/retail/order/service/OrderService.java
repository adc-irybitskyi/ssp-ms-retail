package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO addOrder(OrderDTO order) {
        return toOrderDTO(orderRepository.save(toOrderEntity(order)));
    }

    private OrderDTO toOrderDTO(OrderEntity order) {
        return new OrderDTO()
            .setOrderId(order.getOrderId())
            .setStoreId(order.getStoreId())
            .setOrderDate(order.getOrderDate())
            .setStatus(order.getStatus())
            //.setProducts() TODO: Implement it
            .setFirstName(order.getFirstName())
            .setLastName(order.getLastName())
            .setEmail(order.getEmail())
            .setPhone(order.getPhone());
    }

    private OrderEntity toOrderEntity(OrderDTO order) {
        return new OrderEntity()
            .setStoreId(order.getStoreId())
            .setOrderDate(order.getOrderDate() != null ? order.getOrderDate() : new Date())
            .setStatus(order.getStatus())
            //.setProducts() TODO: Implement it
            .setFirstName(order.getFirstName())
            .setLastName(order.getLastName())
            .setEmail(order.getEmail())
            .setPhone(order.getPhone());
    }
}
