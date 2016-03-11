package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.exception.ProductNotAvailableException;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.rest.dto.OrderProductDTO;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import com.tenx.ms.retail.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockService stockService;

    public OrderDTO addOrder(OrderDTO order) {
        order.getProducts().forEach(p -> checkProductAvailability(order.getStoreId(), p));
        OrderEntity entity = orderRepository.save(toOrderEntity(order));
        //TODO: Update product count after saving order
        return toOrderDTO(entity);
    }

    private void checkProductAvailability(Long storeId, OrderProductDTO p) {
        if (!stockService.isAvailableStock(new StockDTO()
            .setStoreId(storeId)
            .setProductId(p.getProductId())
            .setCount(p.getCount())
        )) {
            throw new ProductNotAvailableException("Product id " + p.getProductId() + " is not available in store id " + storeId);
        }
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
