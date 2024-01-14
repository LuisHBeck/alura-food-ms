package br.com.alurafood.orders.model;

public enum PaymentStatus {
    CREATED,
    CONFIRMED,
    CONFIRMED_WITH_PENDING_INTEGRATION,
    CANCELED
}
