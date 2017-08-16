package com.oblenergo.chat.configuration;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

  private String host;
  private int port;

  @Autowired
  public EmailConfiguration(@Value("${mail.server.host}") String host, @Value("${mail.server.port}") Integer port) {
    this.host = host;
    this.port = port;
  }

  @Bean
  public JavaMailSenderImpl javaMailSenderImpl() {

    System.out.println("--- " + host + " ---  " + port);

    JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
    mailSenderImpl.setHost(host);
    mailSenderImpl.setPort(port);
    return mailSenderImpl;
  }

  @Bean
  public MimeMessage mimeMessage(JavaMailSenderImpl javaMailSenderImpl) {
    MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
    return mimeMessage;
  }

}
