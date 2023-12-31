package br.com.alurafood.orders.model;

import br.com.alurafood.orders.dto.OrderItemRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    private String description;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Order order;

    public OrderItem(OrderItemRequestDto data) {
        this.amount = data.amount();
        this.description = data.description();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
