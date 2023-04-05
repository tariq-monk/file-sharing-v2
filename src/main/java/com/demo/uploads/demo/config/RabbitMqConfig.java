package com.demo.uploads.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Value(value = "${spring.rabbitmq.host}")
    private String RABBIT_HOST;

    @Value(value = "${spring.rabbitmq.port}")
    private int RABBIT_PORT;

    @Value(value = "${spring.rabbitmq.username}")
    private String RABBIT_USERNAME;

    @Value(value = "${spring.rabbitmq.password}")
    private String RABBIT_PASSWORD;

    @Value(value = "${sfox.file-share.rabbitmq.file_exchange_topic}")
    private String FILE_EXCHANGE_TOPIC;

    @Value(value = "${sfox.file-share.rabbitmq.file_changes_queue}")
    private String FILE_EXCHANGE_QUEUE;

    @Value(value = "${sfox.file-share.rabbitmq.file_routing_key}")
    private String FILE_ROUTING_KEY;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(RABBIT_HOST);
        connectionFactory.setPort(RABBIT_PORT);
        connectionFactory.setUsername(RABBIT_USERNAME);
        connectionFactory.setPassword(RABBIT_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange fileExchange() {
        return new DirectExchange(FILE_EXCHANGE_TOPIC);
    }

    @Bean
    public Binding fileChangesBinding(Queue fileChangeQueue, DirectExchange fileExchange) {
        return BindingBuilder.bind(fileChangeQueue).to(fileExchange).with(FILE_ROUTING_KEY);
    }

    @Bean
    public Queue fileChangeQueue() {
        return new Queue(FILE_EXCHANGE_QUEUE);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}