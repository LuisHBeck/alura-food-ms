package br.com.alurafood.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDto(
        @NotNull
        @Positive
        Integer amount,

        @NotBlank
        @NotNull
        String description
) {
}
