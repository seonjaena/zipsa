package com.project.zipsa.unit.mq.fridujo;

import com.github.fridujo.rabbitmq.mock.AmqArguments;
import com.github.fridujo.rabbitmq.mock.ReceiverRegistry;
import com.github.fridujo.rabbitmq.mock.exchange.MockDirectExchange;
import com.rabbitmq.client.AMQP;

import java.util.concurrent.TimeUnit;

public class FixDelayExchange extends MockDirectExchange {
    public FixDelayExchange(String name, AmqArguments arguments, ReceiverRegistry receiverRegistry) {
        super(name, arguments, receiverRegistry);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean publish(String previousExchangeName, String routingKey, AMQP.BasicProperties props, byte[] body) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e) {}
        return super.publish(previousExchangeName, routingKey, props, body);
    }
}
