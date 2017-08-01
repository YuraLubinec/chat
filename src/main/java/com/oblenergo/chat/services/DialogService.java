package com.oblenergo.chat.services;

import java.util.List;

import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.DialogDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;

public interface DialogService {

  ConnectMessageDTO createDialogAndReturnId(String id);

  void addOperatorToTheDialog(ConnectMessageDTO message);

  void saveMessageFromOperator(WebSocketMessageDTO message);

  void saveMessageFromClient(WebSocketMessageDTO message);

  void addChatEndedMessage(String dialog_id);
  
  List<DialogDTO> getAllOperatorDialogs(String operator);
  
  List <DialogDTO> getAllCustomerDialogs(String customer);

  List<DialogDTO> getAllOperatorDialogsForDate(String operator, String date);

  List<DialogDTO> getAllCustomerDialogsForDate(String customerId, String date);

//  ConnectMessageDTO createDialogAndReturnIdJur(String id);
//
//  ConnectMessageDTO createDialogAndReturnIdPhys(String id);

}
