package com.oblenergo.chat.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dao.DialogDao;
import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.DialogDTO;
import com.oblenergo.chat.dto.MessageDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;
import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;
import com.oblenergo.chat.repositories.DialogRepository;

@Service
public class DialogServiceImpl implements DialogService {

  private static final String CHAT_ENDED = "Оператор завершив чат";
  private static final String DATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
  private ZoneId zoneId = ZoneId.systemDefault();

  @Autowired
  private DialogRepository dialogRepository;

  @Autowired
  private DialogDao dialogDao;

//  @Autowired
//  private StatisticService statisticService;

  @Override
  public ConnectMessageDTO createDialogAndReturnId(String id) {

    LocalDateTime dt = LocalDateTime.now();
    Dialog dialog = new Dialog();
    dialog.setCustomerId(id);
    dialog.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogRepository.insert(dialog);
    return createAndReturnConnectMessageDTO(dialog, id);
  }
  // create dialog and add record to statistic
  // @Override
  // public ConnectMessageDTO createDialogAndReturnIdPhys(String id) {
  //
  // LocalDateTime dt = LocalDateTime.now();
  // Dialog dialog = new Dialog();
  // dialog.setCustomerId(id);
  // dialog.setDate(Date.from(dt.atZone(zoneId).toInstant()));
  // dialogRepository.insert(dialog);
  // statisticService.saveStatisticForPhysCustomer(id, Reasons.CONSULTATION);
  // return createAndReturnConnectMessageDTO(dialog, id);
  // }
  //
  // @Override
  // public ConnectMessageDTO createDialogAndReturnIdJur(String id) {
  //
  // LocalDateTime dt = LocalDateTime.now();
  // Dialog dialog = new Dialog();
  // dialog.setCustomerId(id);
  // dialog.setDate(Date.from(dt.atZone(zoneId).toInstant()));
  // dialogRepository.insert(dialog);
  // statisticService.saveStatisticForJurCustomer(id, Reasons.CONSULTATION);
  // return createAndReturnConnectMessageDTO(dialog, id);
  // }

  private ConnectMessageDTO createAndReturnConnectMessageDTO(Dialog dialog, String id) {

    ConnectMessageDTO dto = new ConnectMessageDTO();
    dto.setDialog_id(dialog.getId());
    dto.setId(id);
    return dto;
  }

  @Async
  @Override
  public void addOperatorToTheDialog(ConnectMessageDTO message) {

    Dialog dialog = dialogRepository.findOne(message.getDialog_id());
    dialog.setHoldTime(calculateHoldTime(dialog));
    dialog.setOperator(message.getId());
    dialogRepository.save(dialog);
  }

  private long calculateHoldTime(Dialog dialog) {

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime dt = dialog.getDate().toInstant().atZone(zoneId).toLocalDateTime();
    return Duration.between(dt, now).getSeconds();
  }

  @Async
  @Override
  public void saveMessageFromOperator(WebSocketMessageDTO message) {

    LocalDateTime dt = LocalDateTime.now();
    Message m = new Message();
    m.setText(message.getText());
    m.setOperator_login("operator");
    m.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogDao.findAndPushMessage(message.getDialog_id(), m);
  }

  @Async
  @Override
  public void saveMessageFromClient(WebSocketMessageDTO message) {

    LocalDateTime dt = LocalDateTime.now();
    Message m = new Message();
    m.setText(message.getText());
    m.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogDao.findAndPushMessage(message.getDialog_id(), m);
  }

  @Async
  @Override
  public void addChatEndedMessage(String dialog_id) {

    LocalDateTime dt = LocalDateTime.now();
    Message m = new Message();
    m.setText(CHAT_ENDED);
    m.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogDao.findAndPushMessage(dialog_id, m);
  }

  @Override
  public List<DialogDTO> getAllOperatorDialogs(String operator) {

    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByOperatorIgnoreCaseOrderByDateDesc(operator)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }

  @Override
  public List<DialogDTO> getAllCustomerDialogs(String customerId) {

    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByCustomerIdIgnoreCaseOrderByDateDesc(customerId)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }
  
  @Override
  public List<DialogDTO> getAllOperatorDialogsForDate(String operator, String date) {
   
    System.out.println(date);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByOperatorAndDateAllIgnoreCaseOrderByDateDesc(operator, new Date())) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }
  
  @Override
  public List<DialogDTO> getAllCustomerDialogsForDate(String customerId, String date) {
    
    System.out.println(date);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByCustomerIdAndDateAllIgnoreCaseOrderByDateDesc(customerId, new Date())) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }

  private DialogDTO convertDialog(Dialog dialog) {

    LocalDateTime dateTime = dialog.getDate().toInstant().atZone(zoneId).toLocalDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIMEPATTERN);
    DialogDTO convertedDialog = new DialogDTO();
    convertedDialog.setId(dialog.getId());
    convertedDialog.setOperator(dialog.getOperator());
    convertedDialog.setCustomerId(dialog.getCustomerId());
    convertedDialog.setHoldTime(dialog.getHoldTime());
    convertedDialog.setRedirectTo(dialog.getRedirectTo());
    convertedDialog.setDate(dateTime.format(formatter));
    convertedDialog.setMessages(dialog.getMessages().stream().map(message -> convertMesssage(message, formatter)).collect(Collectors.toList()));
    return convertedDialog;
  }

  private MessageDTO convertMesssage(Message message, DateTimeFormatter formatter) {

    LocalDateTime messageDatetime = message.getDate().toInstant().atZone(zoneId).toLocalDateTime();
    MessageDTO convertedMessage = new MessageDTO();
    convertedMessage.setOperator_login(message.getOperator_login());
    convertedMessage.setText(message.getText());
    convertedMessage.setDate(messageDatetime.format(formatter));
    return convertedMessage;
  }

}
