package com.tutorialspoint.emailapp;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@RestController
public class EmailController {
   @GetMapping(value = "/sendemail")
   public String sendEmail() throws AddressException, MessagingException, IOException {
      sendmail();
      return "Email sent successfully";
   }   

   private void sendmail() throws AddressException, MessagingException, IOException {
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Session session = Session.getInstance(props, new Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("tutorialspoint@gmail.com", "<your password>");
         }
      });
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("tutorialspoint@gmail.com", false));

      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tutorialspoint@gmail.com"));
      msg.setSubject("Tutorials point email");
      msg.setContent("Tutorials point email", "text/html");
      msg.setSentDate(new Date());

      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent("Tutorials point email", "text/html");

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      MimeBodyPart attachPart = new MimeBodyPart();

      attachPart.attachFile("/var/tmp/image19.png");
      multipart.addBodyPart(attachPart);
      msg.setContent(multipart);
      Transport.send(msg);   
   }
}