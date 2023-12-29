package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.model.Payment;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PaymentRequestDto(
        @NotNull
        @Positive
        BigDecimal value,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 19)
        String number,

        @NotBlank
        @Size(max = 7)
        String expiration,

        @NotBlank
        @Size(min = 3, max = 3)
        String code,

        @NotNull
        @JsonAlias({"order"})
        Long orderId,

        @NotNull
        @JsonAlias({"method"})
        Long paymentMethod
        ) {

        public PaymentRequestDto(Payment payment) {
                this(
                        payment.getValue(),
                        payment.getName(),
                        payment.getNumber(),
                        payment.getExpiration(),
                        payment.getCode(),
                        payment.getOrderId(),
                        payment.getPaymentMethod()
                );
        }
}
