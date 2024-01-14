package br.com.alurafood.orders.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentAMQPConfigs {

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connection) {
        return new RabbitAdmin(connection);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // connect to the payments exchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("payments.exchange").build();
    }

    // create a queue to receive payments messages
    @Bean
    public Queue orderDetailQueue() {
        return QueueBuilder.nonDurable("payments.order-detail")
                .deadLetterExchange("payments.dlx")
                .build();
    }

    // binding the queue to the exchange, for the queue register the exchanges receive messages
    @Bean
    public Binding bindingOrderPayment() {
        return BindingBuilder.bind(orderDetailQueue()).to(fanoutExchange());
    }

    // create new exchange for payments DLQ (DEAD LETTERS QUEUE)
    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange("payments.dlx");
    }

    // create a queue to receive dead letters from "payment.order-detail" queue
    // dlq = DEAD LETTER QUEUE
    @Bean
    public Queue orderDetailDLQ() {
        return QueueBuilder.nonDurable("payments.order-detail-dlq").build();
    }

    // binding the dlq to the exchange
    // dlx = DEAD LETTER EXCHANGE
    @Bean
    public Binding bindingOrderPaymentDLX() {
        return BindingBuilder.bind(orderDetailDLQ()).to(deadLetterExchange());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connection, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
