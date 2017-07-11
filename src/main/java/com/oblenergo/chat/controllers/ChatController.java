package com.oblenergo.chat.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  
  @MessageMapping("/connect")
  @SendTo("/topic/allChat")
  public String testWebSocket(String message){
  
    System.out.println(message);
    return "Hello "+ message + "!";
  }

}
