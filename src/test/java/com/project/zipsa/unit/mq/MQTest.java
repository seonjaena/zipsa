package com.project.zipsa.unit.mq;

import com.project.zipsa.config.MockRabbitMQConfig;
import com.project.zipsa.util.mq.MessageListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockRabbitMQConfig.class, MessageListenerAdapter.class})
public class MQTest {
    @Autowired
    private TestRabbitTemplate template;
    @Autowired
    private MessageListener listener;
    @Autowired
    private MockRabbitMQConfig config;

    @Test
    public void test() throws InterruptedException {
        this.template.convertAndSend(config.dlq, "hello1");
        listener.getLatch().await(2000, TimeUnit.MILLISECONDS);
        Assertions.assertThat(this.config.fooIn).isEqualTo("foo:hello1");
        this.template.convertAndSend(config.queue, "hello2");
        listener.getLatch().await(2000, TimeUnit.MILLISECONDS);
        Assertions.assertThat(this.config.barIn).isEqualTo("bar:hello2");

        Assertions.assertThat(this.config.fooIn).isEqualTo("foo:hello1");
        Assertions.assertThat(this.config.barIn).isEqualTo("bar:hello2");
    }
}
