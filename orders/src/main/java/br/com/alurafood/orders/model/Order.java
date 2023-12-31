package br.com.alurafood.orders.model;

import br.com.alurafood.orders.dto.OrderRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> orderItemList;

    public Order(OrderRequestDto data) {
        this.orderItemList = new ArrayList<>();
        data.orderItemList().forEach(item -> {
            var orderItem = new OrderItem(item);
            orderItem.setOrder(this);
            this.orderItemList.add(orderItem);
        });
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRequested() {
        this.status = Status.REQUESTED;
    }

    public void setDate() {
        this.date = LocalDateTime.now();
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void approvePayment() {
        this.status = Status.PAID;
    }
}
