package br.com.alurafood.payments.model;

import br.com.alurafood.payments.dto.PaymentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    private String name;

    private String number;

    private String expiration;

    private String code;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long orderId;

    private Long paymentMethod;


    // CONSTRUCTOR FOR REQUEST DTO
    public Payment(PaymentRequestDto data) {
        this.value = data.value();
        this.name = data.name();
        this.number = data.number();
        this.expiration = data.expiration();
        this.code = data.code();
        this.orderId = data.orderId();
        this.paymentMethod = data.paymentMethod();
    }

    public void setPaymentCreated() {
        this.status = Status.CREATED;
    }

    public void setPaymentConfirmed() {
        this.status = Status.CONFIRMED;
    }

    public void setPaymentConfirmedWithoutIntegration() {
        this.status = Status.CONFIRMED_W_INTEGRATION;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
