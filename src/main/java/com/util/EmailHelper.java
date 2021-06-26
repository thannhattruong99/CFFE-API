package com.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.util.regex.Pattern;

public class EmailHelper {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String HOST_EMAIL = "funny1demo@gmail.com";
    public static final String PASSWORD = "Funny_Demo";

//    public static boolean sendEmail(String to, String messageStr) throws MessagingException {
//        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//        Properties props = System.getProperties();
//        props.setProperty("mail.smtp.host", "smtp.gmail.com");
//        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.store.protocol", "pop3");
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.ssl.enable", "true");
//
//        Session session = Session.getDefaultInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(HOST_EMAIL, PASSWORD);
//            }
//        });
//
//        MimeMessage message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(HOST_EMAIL));
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//        message.setSubject("ping");
//        message.setText(messageStr);
//
//        Transport.send(message);
//        return true;
//
//    }

    static final String SENDGRID_API_KEY = "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";
    static final String SENDGRID_SENDER = "funny1demo@gmail.com";

    public static boolean sendEmail(String toMail, String messageStr) throws IOException {
        // Set content for request.
        Email to = new Email(toMail);
        Email from = new Email(SENDGRID_SENDER);
        String subject = "This is a test email";
        Content content = new Content("text/plain", messageStr);
        Mail mail = new Mail(from, subject, to, content);

        // Instantiates SendGrid client.
        SendGrid sendgrid = new SendGrid(SENDGRID_API_KEY);

        // Instantiate SendGrid request.
        Request request = new Request();

        // Set request configuration.
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        // Use the client to send the API request.
        Response response = sendgrid.api(request);

        if (response.getStatusCode() != 202) {
            System.out.print(String.format("An error occurred: %s", response.getStatusCode()));
            return false;
        }

        System.out.print("Email sent.");
        return true;
    }
}
