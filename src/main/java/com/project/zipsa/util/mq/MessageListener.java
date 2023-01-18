package com.project.zipsa.util.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

public class MessageListener {

    private CountDownLatch latch = new CountDownLatch(1);

    public void handleMessage(String message) {
        System.out.println("<== Received : " + message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
