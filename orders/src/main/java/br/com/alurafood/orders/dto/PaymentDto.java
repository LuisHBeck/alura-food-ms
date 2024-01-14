package br.com.alurafood.orders.dto;

import br.com.alurafood.orders.model.PaymentStatus;

import java.math.BigDecimal;

public record PaymentDto(
        Long id,
        BigDecimal value,
        String name,
        String number,
        String expiration,
        String code,
        PaymentStatus status,
        Long orderId,
        Long paymentMethod) {
}
