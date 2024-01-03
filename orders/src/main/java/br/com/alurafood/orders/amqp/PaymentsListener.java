package br.com.alurafood.orders.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentsListener {

    @RabbitListener(queues = "payment.finished")
    public void receiveMessage(Message message) {
        System.out.println(String.format("Receive the following message: %s", message.toString()));
    }
}
