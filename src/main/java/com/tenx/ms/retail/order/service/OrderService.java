package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.rest.dto.OrderProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

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
            .setProducts(order.getProducts().stream()
                .map(this::toOrderProductDTO)
                .collect(Collectors.toSet()))
            .setFirstName(order.getFirstName())
            .setLastName(order.getLastName())
            .setEmail(order.getEmail())
            .setPhone(order.getPhone());
    }

    private OrderEntity toOrderEntity(OrderDTO order) {
        OrderEntity entity = new OrderEntity();
            entity.setStoreId(order.getStoreId())
            .setOrderDate(order.getOrderDate() != null ? order.getOrderDate() : new Date())
            .setStatus(order.getStatus())
            .setProducts(order.getProducts().stream()
                .map(p -> toOrderProductEntity(entity, p))
                .collect(Collectors.toSet()))
            .setFirstName(order.getFirstName())
            .setLastName(order.getLastName())
            .setEmail(order.getEmail())
            .setPhone(order.getPhone());
        return entity;
    }

    private OrderProductEntity toOrderProductEntity(OrderEntity order, OrderProductDTO product) {
        return new OrderProductEntity()
            .setOrder(order)
            .setProductId(product.getProductId())
            .setCount(product.getCount());
    }

    private OrderProductDTO toOrderProductDTO(OrderProductEntity product) {
        return new OrderProductDTO()
            .setProductId(product.getProductId())
            .setCount(product.getCount());
    }
}
