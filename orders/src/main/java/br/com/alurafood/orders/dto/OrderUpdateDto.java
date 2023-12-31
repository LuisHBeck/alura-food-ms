package br.com.alurafood.orders.dto;

import br.com.alurafood.orders.model.Status;
import jakarta.validation.constraints.NotBlank;

public record OrderUpdateDto(
        @NotBlank
        Status status
) {
}
