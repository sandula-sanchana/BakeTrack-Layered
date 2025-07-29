package edu.ijse.BakeTrack.dao;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class WhatsAppSender {
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String FROM_NUMBER = "whatsapp:+14155238886";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendMessage(String toNumber, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + toNumber),
                new com.twilio.type.PhoneNumber(FROM_NUMBER),
                messageBody
        ).create();

        System.out.println("Message sent! SID: " + message.getSid());
    }
}