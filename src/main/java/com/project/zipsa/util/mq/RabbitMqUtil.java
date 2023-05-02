package com.project.zipsa.util.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "prod"})
@Component
@RequiredArgsConstructor
public class RabbitMqUtil {

    private final RabbitAdmin rabbitAdmin;

    public void createQueue(String queueName) {
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);
    }

    public void createBinding(String queueName, String exchangeName, String routingKey) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        rabbitAdmin.declareBinding(binding);
    }

}
