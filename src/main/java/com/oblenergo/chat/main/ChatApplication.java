package com.oblenergo.chat.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.oblenergo.chat", exclude = SecurityAutoConfiguration.class )
@PropertySource("classpath:serviceSecure.properties")
public class ChatApplication {

  public static void main(String[] args) {

    SpringApplication.run(ChatApplication.class, args);
  }
}
