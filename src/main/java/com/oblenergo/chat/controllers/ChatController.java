package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.oblenergo.chat.dto.ActiveChatConnectionDTO;
import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.EndConnectionDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;
import com.oblenergo.chat.services.DialogService;

@Controller
public class ChatController {

  @Autowired
  private DialogService dialogService;

  @MessageMapping("/connect/physical/{client_id}")
  @SendTo("/topic/allChat")
  public ConnectMessageDTO getConnectionForPhysicalCustomer(@DestinationVariable String client_id) {

    return dialogService.createDialogAndReturnIdPhys(client_id);
  }

  @MessageMapping("/connect/juridical/{client_id}")
  @SendTo("/topic/allChat")
  public ConnectMessageDTO getConnectionForJuridicalCustomer(@DestinationVariable String client_id) {

    return dialogService.createDialogAndReturnIdJur(client_id);
  }

  @MessageMapping("/checkClientRequestArray")
  @SendTo("/topic/checkClientRequestArray")
  public String removeActiveChatFromSubscriptionArray(ActiveChatConnectionDTO dto) {

    return dto.getCustomerId();
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
    return message;
  }

  @MessageMapping("/client/{client_id}")
  @SendTo("/queue/{client_id}")
  public WebSocketMessageDTO sendMessageToClient(WebSocketMessageDTO message) {

    dialogService.saveMessageFromOperator(message);
    return message;
  }

  @MessageMapping("/endNotification/{client_id}/{dialog_id}")
  @SendTo("/queue/{client_id}")
  public EndConnectionDTO notifyAboutConnectionEnd(@DestinationVariable String dialog_id) {

    dialogService.addChatEndedMessage(dialog_id);
    return new EndConnectionDTO();
  }

}
