package com.oblenergo.chat.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dto.DialogDTO;
import com.oblenergo.chat.dto.MessageDTO;
import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;
import com.oblenergo.chat.repositories.DialogRepository;

@Service
public class SearchServiceImpl implements SearchService {
  
  private static final long ONE_DAY = 1;
  private static final String DATEPATTERN = "yyyy-MM-dd";
  private static final String DATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
  private ZoneId zoneId = ZoneId.systemDefault();
  
  @Autowired
  private DialogRepository dialogRepository;
  
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
    
    LocalDate dt = parseToLocalDate(date); 
    LocalDate dtnextDay = dt.plusDays(ONE_DAY);
    Date begin = convertToDate(dt);
    Date end = convertToDate(dtnextDay);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByOperatorIgnoreCaseAndDateBetweenOrderByDateDesc(operator, begin, end)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }
  
  @Override
  public List<DialogDTO> getAllCustomerDialogsForDate(String customerId, String date) {
    
    LocalDate dt = parseToLocalDate(date); 
    LocalDate dtnextDay = dt.plusDays(ONE_DAY);
    Date begin = convertToDate(dt);
    Date end = convertToDate(dtnextDay);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByCustomerIdIgnoreCaseAndDateBetweenOrderByDateDesc(customerId, begin, end)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }
  
  @Override
  public List<DialogDTO> getAllDialogsForDate(String date) {
    
    LocalDate dt = parseToLocalDate(date); 
    LocalDate dtnextDay = dt.plusDays(ONE_DAY);
    Date begin = convertToDate(dt);
    Date end = convertToDate(dtnextDay);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByDateBetweenOrderByDateDesc(begin, end)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }

  @Override
  public List<DialogDTO> getAllOperatorAndCustomerDialogs(String operator, String customerId) {
    
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByCustomerIdAndOperatorAllIgnoreCaseOrderByDateDesc(customerId, operator)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }

  @Override
  public List<DialogDTO> getAllOperatorAndCustomerDialogsForDate(String operator, String customerId, String date) {
    
    LocalDate dt = parseToLocalDate(date); 
    LocalDate dtnextDay = dt.plusDays(ONE_DAY);
    Date begin = convertToDate(dt);
    Date end = convertToDate(dtnextDay);
    List<DialogDTO> dialogs = null;
    try (Stream<Dialog> dialogStream = dialogRepository.findByCustomerIdIgnoreCaseAndOperatorIgnoreCaseAndDateBetweenOrderByDateDesc(customerId, operator, begin, end)) {
      dialogs = dialogStream.map(dialog -> convertDialog(dialog)).collect(Collectors.toList());
    }
    return dialogs;
  }
  
  private LocalDate parseToLocalDate(String date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEPATTERN);
    return LocalDate.parse(date, formatter); 
  }
  
  private Date convertToDate(LocalDate dt){
    return Date.from(dt.atStartOfDay(zoneId).toInstant());
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
