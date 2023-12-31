package br.com.alurafood.orders.repository;

import br.com.alurafood.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
