package drivus.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "appointmentStatusExchange";
    public static final String ROUTING_KEY = "appointmentStatusCreated";

    @Bean
    public TopicExchange appointmentStatusExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appointmentStatusQueue() {
        return new Queue("appointmentStatusQueue");
    }

    @Bean
    public Binding binding(Queue appointmentStatusQueue, TopicExchange appointmentStatusExchange) {
        return BindingBuilder.bind(appointmentStatusQueue).to(appointmentStatusExchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}