package br.com.alurafood.orders.dto;

import br.com.alurafood.orders.model.Order;
import br.com.alurafood.orders.model.OrderItem;
import br.com.alurafood.orders.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailingDto(
        Long id,
        LocalDateTime date,
        OrderStatus status,
        List<OrderItem> orderItem
) {
    public OrderDetailingDto(Order order) {
        this(order.getId(),
                order.getDate(),
                order.getStatus(),
                List.copyOf(order.getOrderItemList())
        );
    }
}
