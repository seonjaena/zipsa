package com.project.zipsa.unit.mq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Import(MockRabbitMQConfig.class)
public class MQTest02 {

    private static final String QUEUE_NAME1 = UUID.randomUUID().toString();
    private static final String QUEUE_NAME2 = UUID.randomUUID().toString();
    private static final String EXCHANGE_NAME = UUID.randomUUID().toString();

    @Test
    void basic_get_case() {
        String messageBody = "Hello World!";
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MockRabbitMQConfig.class)) {
            RabbitTemplate rabbitTemplate = queueAndExchangeSetup(context);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "test1.key1", messageBody);
            Message message = rabbitTemplate.receive(QUEUE_NAME1);

            assertThat(message).isNotNull();
            assertThat(message.getBody()).isEqualTo(messageBody.getBytes());
        }
    }

    @Test
    void basic_get_topic() {
        String messageBody = "Hello World!";
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MockRabbitMQConfig.class)) {
            RabbitTemplate rabbitTemplate = queueAndExchangeSetup(context);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "test1.key1", messageBody + "1");
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "test2.key1", messageBody + "2");
            Message message1 = rabbitTemplate.receive(QUEUE_NAME1);
            Message message2 = rabbitTemplate.receive(QUEUE_NAME2);

            assertThat(message1).isNotNull();
            assertThat(message1.getBody()).isEqualTo((messageBody + 1).getBytes());
            assertThat(message2).isNotNull();
            assertThat(message2.getBody()).isEqualTo((messageBody + 2).getBytes());
        }
    }

    @Test
    void basic_consume_case() {
        String messageBody = "Hello World!";
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MockRabbitMQConfig.class)) {
            RabbitTemplate rabbitTemplate = queueAndExchangeSetup(context);

            Receiver receiver = new Receiver();
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(context.getBean(ConnectionFactory.class));
            container.setQueueNames(QUEUE_NAME1);
            container.setMessageListener(new MessageListenerAdapter(receiver, "receiveMessage"));
            try {
                container.start();

                rabbitTemplate.convertAndSend(EXCHANGE_NAME, "test1.key2", messageBody);

                List<String> receivedMessages = new ArrayList<>();
                assertTimeoutPreemptively(Duration.ofMillis(500L), () -> {
                    while(receivedMessages.isEmpty()) {
                        receivedMessages.addAll(receiver.getMessages());
                        TimeUnit.MILLISECONDS.sleep(100L);
                    }
                });
                assertThat(receivedMessages).containsExactly(messageBody);
            }finally {
                container.stop();
            }
        }
    }

    @Test
    void reply_direct_to() throws ExecutionException, InterruptedException {
        String messageBody = "Hello World!";
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MockRabbitMQConfig.class)) {
            RabbitTemplate rabbitTemplate = queueAndExchangeSetup(context);
            AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(rabbitTemplate);

            Receiver receiver = new Receiver();
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(context.getBean(ConnectionFactory.class));
            container.setQueueNames(QUEUE_NAME1);
            container.setMessageListener(new MessageListenerAdapter(receiver, "receiveMessageAndReply"));

            try {
                container.start();
                asyncRabbitTemplate.start();

                AsyncRabbitTemplate.RabbitConverterFuture<Object> result = asyncRabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "test1.key2", messageBody);

                assertThat(result.get()).isEqualTo(new StringBuilder(messageBody).reverse().toString());
                assertThat(receiver.getMessages()).containsExactly(messageBody);
            }finally {
                container.stop();
                asyncRabbitTemplate.stop();
            }
        }
    }


    private RabbitTemplate queueAndExchangeSetup(BeanFactory context) {
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);

        Queue queue1 = new Queue(QUEUE_NAME1, false);
        Queue queue2 = new Queue(QUEUE_NAME2, false);
        rabbitAdmin.declareQueue(queue1);
        rabbitAdmin.declareQueue(queue2);
        TopicExchange exchange = new TopicExchange(EXCHANGE_NAME);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with("test1.*"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with("test2.*"));
        return context.getBean(RabbitTemplate.class);
    }

    static class Receiver {
        private final List<String> message = new ArrayList<>();

        public void receiveMessage(String message) {
            this.message.add(message);
        }

        public String receiveMessageAndReply(String message) {
            this.message.add(message);
            return new StringBuilder(message).reverse().toString();
        }

        List<String> getMessages() {
            return message;
        }
    }

}
