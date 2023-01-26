package com.project.zipsa.unit.mq.fridujo;

import com.github.fridujo.rabbitmq.mock.compatibility.MockConnectionFactoryFactory;
import com.github.fridujo.rabbitmq.mock.exchange.MockExchangeCreator;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockRabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PooledChannelConnectionFactory(
                MockConnectionFactoryFactory
                        .build()
                        .enableConsistentHashPlugin()
                        .withAdditionalExchange(MockExchangeCreator.creatorWithExchangeType("x-fix-delayed-message", FixDelayExchange::new))
        );
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}
