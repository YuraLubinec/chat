package com.oblenergo.chat.services;

public interface MailService {

  void sendMail(String clientId, String text, String clientEmail);
}
