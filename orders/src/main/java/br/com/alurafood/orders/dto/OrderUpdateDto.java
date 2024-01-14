package br.com.alurafood.orders.dto;

import br.com.alurafood.orders.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;

public record OrderUpdateDto(
        @NotBlank
        OrderStatus status
) {
}
