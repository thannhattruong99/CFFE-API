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

    private static final String SENDGRID_SENDER = "henrymaskhm1@gmail.com";

    public static boolean sendEmail(String toMail, String messageStr) throws Exception {
        // Set content for request.
        Email to = new Email(toMail);
        Email from = new Email(SENDGRID_SENDER);
        String subject = "CFFE ACCOUNT!";
        Content content = new Content("text/plain", messageStr);
        Mail mail = new Mail(from, subject, to, content);

        // Instantiates SendGrid client.
        SendGrid sendgrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        // Instantiate SendGrid request.
        Request request = new Request();

        // Set request configuration.
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        // Use the client to send the API request.
        Response response = sendgrid.api(request);

        if (response.getStatusCode() != 202) {
            throw new Exception(String.format("An error occurred: %s", response.getStatusCode()));
        }

        return true;
    }
}
