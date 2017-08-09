package com.oblenergo.chat.services;

import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;

public interface DialogService {

  void addOperatorToTheDialog(ConnectMessageDTO message);

  void saveMessageFromOperator(WebSocketMessageDTO message);

  void saveMessageFromClient(WebSocketMessageDTO message);

  void addChatEndedMessage(String dialog_id);

  ConnectMessageDTO createDialogAndReturnIdJur(String id);

  ConnectMessageDTO createDialogAndReturnIdPhys(String id);

//  ConnectMessageDTO createDialogAndReturnIdJur(String id);
//
//  ConnectMessageDTO createDialogAndReturnIdPhys(String id);

}
