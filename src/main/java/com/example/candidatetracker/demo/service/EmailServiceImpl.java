package com.example.candidatetracker.demo.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

	@Override
	public void sendEmail(String recipient, String password) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("candidatetracker0@gmail.com", "Candidate@123");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("candidatetracker0@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        msg.setSubject("Account Created");
        msg.setContent("Your account is successfully created. Please use your email id along with attached password to login. Password : \n" + password + " ", "text/html");
        msg.setSentDate(new Date());

        Transport.send(msg);
	}
    
}