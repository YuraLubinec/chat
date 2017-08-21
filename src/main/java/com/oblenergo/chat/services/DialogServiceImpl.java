package com.oblenergo.chat.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dao.DialogDao;
import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;
import com.oblenergo.chat.enums.Reasons;
import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;
import com.oblenergo.chat.repositories.DialogRepository;

@Service
public class DialogServiceImpl implements DialogService {

  private static final String CHAT_ENDED = "Оператор завершив чат";
  private ZoneId zoneId = ZoneId.systemDefault();

  @Autowired
  private DialogRepository dialogRepository;

  @Autowired
  private DialogDao dialogDao;

  @Autowired
  private StatisticService statisticService;

  @Override
  public ConnectMessageDTO createDialogAndReturnIdPhys(String id) {

    LocalDateTime dt = LocalDateTime.now();
    Dialog dialog = new Dialog();
    dialog.setCustomerId(id);
    dialog.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogRepository.insert(dialog);
    statisticService.saveStatisticForPhysCustomer(id, Reasons.CONSULTATION);
    return createAndReturnConnectMessageDTO(dialog, id);
  }

  @Override
  public ConnectMessageDTO createDialogAndReturnIdJur(String id) {

    LocalDateTime dt = LocalDateTime.now();
    Dialog dialog = new Dialog();
    dialog.setCustomerId(id);
    dialog.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogRepository.insert(dialog);
    statisticService.saveStatisticForJurCustomer(id, Reasons.CONSULTATION);
    return createAndReturnConnectMessageDTO(dialog, id);
  }

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
    m.setOperator_login("operator");
    m.setDate(Date.from(dt.atZone(zoneId).toInstant()));
    dialogDao.findAndPushMessage(dialog_id, m);
  }
	
  @Async
  @Override
  public void setRateForOperator(String dialog_id, int rate) {
	  dialogDao.findAndPushRate(dialog_id, rate);
  }

}
