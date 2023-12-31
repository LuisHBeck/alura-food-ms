package br.com.alurafood.orders.dto;

public record OrderItemDetailingDto(
        Long id,
        Integer amount,
        String description
) {
}
