package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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

  @MessageMapping("/connect/{client_id}")
  @SendTo("/topic/allChat")
  public ConnectMessageDTO getConnection(@DestinationVariable String client_id) {
    System.out.println("getConnection" + client_id);
    return dialogService.createDialogAndReturnId(client_id);
  }

  @MessageMapping("/checkClientRequestArray")
  @SendTo("/topic/checkClientRequestArray")
  public String removeActiveChatFromSubscriptionArray(ActiveChatConnectionDTO dto) {
    System.out.println("removeActiveChatFromSubscriptionArray" + dto.getCustomerId());
    return dto.getCustomerId();
  }

  @MessageMapping("/connection/{client_id}")
  @SendTo("/queue/{client_id}")
  public ConnectMessageDTO sendConnectionMessageToClient(ConnectMessageDTO message) {
    System.out.println("sendConnectionMessageToClient" + message);
    dialogService.addOperatorToTheDialog(message);
    return message;
  }

  @MessageMapping("/operator/{operator_name}")
  @SendTo("/queue/{operator_name}")
  public WebSocketMessageDTO sendMessageToOperator(WebSocketMessageDTO message) {
    System.out.println("sendMessageToOperator" + message);
    dialogService.saveMessageFromClient(message);
    WebSocketMessageDTO dto = new WebSocketMessageDTO();
    return message;
  }

  @MessageMapping("/client/{client_id}")
  @SendTo("/queue/{client_id}")
  public WebSocketMessageDTO sendMessageToClient(WebSocketMessageDTO message) {
    System.out.println("sendMessageToClient" + message);
    dialogService.saveMessageFromOperator(message);
    WebSocketMessageDTO dto = new WebSocketMessageDTO();
    return message;
  }

}
