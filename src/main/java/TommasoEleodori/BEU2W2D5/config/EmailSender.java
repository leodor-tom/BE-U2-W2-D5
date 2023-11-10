package TommasoEleodori.BEU2W2D5.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailSender {
    private final String apikey;
    private final String sender;

    public EmailSender(@Value("${sendgrid.apikey}") String apikey,
                       @Value("${sendgrid.sender}") String sender) {
        this.apikey = apikey;
        this.sender = sender;
    }

    public void sendRegistrationEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Registration successful";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "welcome aboard " + name);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendUpdateAccountEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Account Updated";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your account has been updated successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendDeletedAccountEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Account Deleted";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your account has been deleted successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendNewDeviceEmail(String recipient, String name, String type) throws IOException {
        Email from = new Email(sender);
        String subject = "New Device";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your new " + type + "it's registered!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendDeletedDeviceEmail(String recipient, String name, String type) throws IOException {
        Email from = new Email(sender);
        String subject = "Device Deleted";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your device has been deleted successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
