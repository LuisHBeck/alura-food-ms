package br.com.alurafood.payments.amqp;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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

//    @Bean
//    public Queue createQueue() {
//        return new Queue("payment.finished", false);
////        return QueueBuilder.nonDurable("payment.finished").build(); just another way
//    }

    @Bean
    public FanoutExchange createFanoutExchange() {
        return new FanoutExchange("payments.exchange");
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connection) {
        return new RabbitAdmin(connection);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
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
