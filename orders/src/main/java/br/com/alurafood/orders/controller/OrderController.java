package br.com.alurafood.orders.controller;

import br.com.alurafood.orders.dto.OrderDetailingDto;
import br.com.alurafood.orders.dto.OrderRequestDto;
import br.com.alurafood.orders.dto.OrderUpdateDto;
import br.com.alurafood.orders.service.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderDetailingDto>> listOrders(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity listOrderById(@PathVariable Long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createOrder(@RequestBody @Valid OrderRequestDto orderData, UriComponentsBuilder uriBuilder) {
        var order = orderService.createOrder(orderData);
        var uri = uriBuilder.path("/orders/{id}").build(order.id());
        return ResponseEntity.created(uri).body(order);
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity updateOrder(@PathVariable Long id, @RequestBody @Valid OrderUpdateDto orderData) {
        var updatedOrder = orderService.updateOrderStatus(id, orderData);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{id}/paid")
    @Transactional
    public ResponseEntity approveOrderPayment(@PathVariable Long id) {
        var order = orderService.approveOrderPayment(id);
        return ResponseEntity.ok(order);
    }
}
