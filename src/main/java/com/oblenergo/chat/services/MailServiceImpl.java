package com.oblenergo.chat.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

  String sender;
  String receiver;

  public MailServiceImpl(@Value("${mail.sender.address}") String sender,
      @Value("${mail.receiver.address}") String receiver) {

    this.sender = sender;
    this.receiver = receiver;
  }

  @Autowired
  private MimeMessage mimeMessage;

  @Autowired
  private JavaMailSenderImpl senderImpl;

  @Override
  public void sendMail(String clientId, String text) {
    try {

      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

      System.out.println("sender: " + sender);
      System.out.println("receiver: " + receiver);

      messageHelper.setFrom(sender);
      messageHelper.setTo(receiver);
      messageHelper.setText(text);
      messageHelper.setSubject(clientId);
      senderImpl.send(mimeMessage);
    } catch (MessagingException e) {

      e.printStackTrace();
    }

  }

}
