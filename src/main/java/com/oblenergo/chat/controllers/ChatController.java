package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.oblenergo.chat.dto.ActiveChatConnectionDTO;
import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;
import com.oblenergo.chat.services.DialogService;

@Controller
public class ChatController {

  @Autowired
  private DialogService dialogService;

  @MessageMapping("/connect")
  @SendTo("/topic/allChat")
  public ConnectMessageDTO testWebSocket(ConnectMessageDTO id) {

    return dialogService.createDialogAndReturnId(id.getId());
  }

  @MessageMapping("/checkClientRequestArray")
  @SendTo("/topic/checkClientRequestArray")
  public String removeActiveChatFromSubscriptionArray(ActiveChatConnectionDTO dto) {

    return dto.getId();
  }

  @MessageMapping("/connection/{client_id}")
  @SendTo("/queue/{client_id}")
  public ConnectMessageDTO sendConnectionMessageToClient(ConnectMessageDTO message) {
   
    dialogService.addOperatorToTheDialog(message);
    return message;
  }

  @MessageMapping("/operator/{operator_name}")
  @SendTo("/queue/{operator_name}")
  public WebSocketMessageDTO sendMessageToOperator(WebSocketMessageDTO message) {
    
    dialogService.saveMessageFromClient(message);
    WebSocketMessageDTO dto = new WebSocketMessageDTO();
    return dto;
  }

  @MessageMapping("/client/{client_id}")
  @SendTo("/queue/{client_id}")
  public WebSocketMessageDTO sendMessageToClient(WebSocketMessageDTO message) {
    
    dialogService.saveMessageFromOperator(message);
    WebSocketMessageDTO dto = new WebSocketMessageDTO();
    return dto;
  }

}
