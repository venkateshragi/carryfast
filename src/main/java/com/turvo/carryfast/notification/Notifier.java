package com.turvo.carryfast.notification;

import com.turvo.carryfast.entities.Shipper;

public interface Notifier {

    void sendMail(Shipper shipper, Message message);
}
