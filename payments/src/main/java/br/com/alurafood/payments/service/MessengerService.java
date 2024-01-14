package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDetailingDto;
import br.com.alurafood.payments.model.Payment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessengerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishCreatedPaymentMessage(Payment payment) {
        var message = new Message((String.format("Payment created with id %s", payment.getId()).getBytes()));

        var paymentDto = new PaymentDetailingDto(payment);
        rabbitTemplate.convertAndSend("payments.exchange","", payment);
    }
}
