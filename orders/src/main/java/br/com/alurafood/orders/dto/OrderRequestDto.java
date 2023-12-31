package br.com.alurafood.orders.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record OrderRequestDto(
        @JsonAlias({"items"})
        List<OrderItemRequestDto> orderItemList
) {
}
