package com.project.zipsa.unit.mq.spring;

import com.project.zipsa.util.mq.MessageListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.concurrent.TimeUnit;

@SpringJUnitConfig(MockRabbitMQConfig.class)
@RabbitListenerTest
public class MQTest {
    @Autowired
    private TestRabbitTemplate template;
    @Autowired
    private MessageListener listener;
    @Autowired
    private MockRabbitMQConfig config;

    @Test
    public void test() throws InterruptedException {
        this.template.convertAndSend(this.config.dlq, "hello1");
        this.listener.getLatch().await(2000, TimeUnit.MILLISECONDS);
        Assertions.assertThat(this.config.fooIn).isEqualTo("foo:hello1");
        this.template.convertAndSend(this.config.queue, "hello2");
        this.listener.getLatch().await(2000, TimeUnit.MILLISECONDS);
        Assertions.assertThat(this.config.barIn).isEqualTo("bar:hello2");

        Assertions.assertThat(this.config.fooIn).isEqualTo("foo:hello1");
        Assertions.assertThat(this.config.barIn).isEqualTo("bar:hello2");
    }
}
