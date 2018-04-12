package com.turvo.carryfast.notification;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.turvo.carryfast.entities.Shipper;

@Component
public class EmailNotifierImpl implements Notifier {

    @Value("${from.mail.id}")
    private String mailId;

    @Value("${from.mail.password}")
    private String mailPassword;

    @Value("${mail.smtp.host}")
    private String host;


    @Override
    public void sendMail(Shipper shipper, Message message) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", mailId);
        properties.setProperty("mail.password", mailPassword);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(mailId));
            mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(shipper.getEmail()));
            mimeMessage.setSubject("Your shipment arrival status");
            mimeMessage.setText(message.toString());
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
