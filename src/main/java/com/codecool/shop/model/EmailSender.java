package com.codecool.shop.model;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void sender(String userEmailAddress, String userName) {
        final String username = "ppgwebshop@gmail.com";
        final String password = "pindurpandurok";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ppgwebshop@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userEmailAddress));
            message.setSubject("Welcome");
            message.setText("Dear " + userName + ','
                    + "\n\n Welcome in the PowerPuff Shop!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);


        }
    }
}
