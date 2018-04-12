package com.turvo.carryfast.notification;

public interface MessageChannel {

    void addMessage(Message message);

    Message pollMessage();
}
