package com.project.zipsa.config;

import com.rabbitmq.client.Channel;
import org.mockito.BDDMockito;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile({"test"})
@Configuration
@EnableRabbit
public class MockRabbitMQConfig {

    public String fooIn = "";
    public String barIn = "";
    @Value("${spring.rabbitmq.dead-log-queue}")
    public String dlq;
    @Value("${spring.rabbitmq.log-queue}")
    public String queue;

    @Bean
    public TestRabbitTemplate testTemplate() throws IOException {
        return new TestRabbitTemplate(testConnectionFactory());
    }

    @Bean
    public ConnectionFactory testConnectionFactory() throws IOException {
        ConnectionFactory factory = BDDMockito.mock(ConnectionFactory.class);
        Connection connection = BDDMockito.mock(Connection.class);
        Channel channel = BDDMockito.mock(Channel.class);
        BDDMockito.willReturn(connection).given(factory).createConnection();
        BDDMockito.willReturn(channel).given(connection).createChannel(BDDMockito.anyBoolean());
        BDDMockito.given(channel.isOpen()).willReturn(true);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() throws IOException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(testConnectionFactory());
        return factory;
    }

    @RabbitListener(queues = "${spring.rabbitmq.dead-log-queue}")
    public void foo(String in) {
        this.fooIn += "foo:" + in;
    }

    @RabbitListener(queues = "${spring.rabbitmq.log-queue}")
    public void bar(String in) {
        this.barIn += "bar:" + in;
    }

    @Bean
    public SimpleMessageListenerContainer testContainer(ConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(dlq, queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

}
