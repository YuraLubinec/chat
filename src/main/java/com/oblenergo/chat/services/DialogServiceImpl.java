package com.oblenergo.chat.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dao.DialogDao;
import com.oblenergo.chat.dto.ConnectMessageDTO;
import com.oblenergo.chat.dto.WebSocketMessageDTO;
import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;
import com.oblenergo.chat.repositories.DialogRepository;


@Service
public class DialogServiceImpl implements DialogService {

  @Autowired
  private DialogRepository dialogRepository;
  
  @Autowired
  private DialogDao dialogDao;

  @Override
  public ConnectMessageDTO createDialogAndReturnId(String id) {
    
    LocalDateTime dt = LocalDateTime.now();
    Dialog dialog = new Dialog();
    dialog.setCustomer_id(id);
    dialog.setDate(Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()));
    dialogRepository.insert(dialog);    
    return createAndReturnConnectMessageDTO(dialog, id);
  }
  
  private ConnectMessageDTO createAndReturnConnectMessageDTO(Dialog dialog, String id){
  
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
  
  private long calculateHoldTime(Dialog dialog){
    
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime dt = dialog.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    return Duration.between(dt, now).getSeconds();
  }

  @Async
  @Override
  public void saveMessageFromOperator(WebSocketMessageDTO message) {
    
    LocalDateTime dt = LocalDateTime.now();
    LocalDate d = LocalDate.now();
    Message m = new Message();
    m.setText(message.getText());
    m.setOperator_login("operator");
    m.setDate(d.toString());
    m.setTime(dt.getHour()+":"+dt.getMinute()+":"+dt.getSecond());
    dialogDao.findAndPushMessage(message.getDialog_id(), m);
  }

  @Async
  @Override
  public void saveMessageFromClient(WebSocketMessageDTO message) {
   
    LocalDateTime dt = LocalDateTime.now();
    LocalDate d = LocalDate.now();
    Message m = new Message();
    m.setText(message.getText());
    m.setDate(d.toString());
    m.setTime(dt.getHour()+":"+dt.getMinute()+":"+dt.getSecond());
    dialogDao.findAndPushMessage(message.getDialog_id(), m);
  }
  
  
}
