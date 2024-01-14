package br.com.alurafood.orders.amqp;

import br.com.alurafood.orders.dto.PaymentDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentsListener {

    @RabbitListener(queues = "payment.finished")
    public void receiveMessage(PaymentDto payment) {
        String message = String.format("""
                Payment data: %s
                Order number: %s
                Value: R$%s
                Status: %S
                """.formatted(payment.id(), payment.number(), payment.value(), payment.status()));

        System.out.println(String.format("Receive the following message: %s", message));
    }
}
