package com.turvo.carryfast.notification;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class QueueMessageChannelImpl implements MessageChannel {

    private BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    @Override
    public void addMessage(Message message) {
        queue.add(message);
    }

    @Override
    public Message pollMessage() {
        try {
            return queue.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
