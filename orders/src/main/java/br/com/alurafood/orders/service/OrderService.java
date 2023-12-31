package br.com.alurafood.orders.service;

import br.com.alurafood.orders.dto.OrderDetailingDto;
import br.com.alurafood.orders.dto.OrderRequestDto;
import br.com.alurafood.orders.dto.OrderUpdateDto;
import br.com.alurafood.orders.model.Order;
import br.com.alurafood.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderDetailingDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderDetailingDto::new);
    }

    public OrderDetailingDto getOrderById(Long id) {
        var order = orderRepository.getReferenceById(id);
        return new OrderDetailingDto(order);
    }

    @Transactional
    public OrderDetailingDto createOrder(OrderRequestDto data) {
        var order = new Order(data);
        order.setRequested();
        order.setDate();
        orderRepository.save(order);

        return new OrderDetailingDto(order);
    }

    @Transactional
    public OrderDetailingDto updateOrderStatus(Long id, OrderUpdateDto data) {
        var order = orderRepository.getReferenceById(id);
        order.updateStatus(data.status());
        return new OrderDetailingDto(order);
    }

    @Transactional
    public OrderDetailingDto approveOrderPayment(Long id) {
        var order = orderRepository.getReferenceById(id);
        order.approvePayment();
        return new OrderDetailingDto(order);
    }
}
