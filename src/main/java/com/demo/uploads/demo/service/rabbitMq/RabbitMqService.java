package com.demo.uploads.demo.service.rabbitMq;

import com.demo.uploads.demo.events.BaseEvent;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RabbitMqService {

    private final RabbitAdmin rabbitAdmin;
    private final DirectExchange fileDirectExchange;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqService(RabbitAdmin rabbitAdmin, DirectExchange fileDirectExcahnge, RabbitTemplate rabbitTemplate) {
        this.rabbitAdmin = rabbitAdmin;
        this.fileDirectExchange = fileDirectExcahnge;
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T extends BaseEvent> void sendToTopic(String exchangeName, String routingKey, T message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    @PostConstruct
    private void declareExchange() {
        rabbitAdmin.declareExchange(fileDirectExchange);
    }
}