package com.project.zipsa.unit.mq;

import com.project.zipsa.config.MockRabbitMQConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockRabbitMQConfig.class, MessageListenerAdapter.class})
public class MQTest {
    @Autowired
    private TestRabbitTemplate template;

    @Autowired
    private MockRabbitMQConfig config;

    @Test
    public void test() {
        this.template.convertAndSend(config.dlq, "hello1");
        this.template.convertAndSend(config.queue, "hello2");

        Assertions.assertThat(this.config.fooIn).isEqualTo("foo:hello1");
        Assertions.assertThat(this.config.barIn).isEqualTo("bar:hello2");
    }
}
