package com.oblenergo.chat.services;

import java.util.List;

import com.oblenergo.chat.dto.DialogDTO;

public interface SearchService {

  List<DialogDTO> getAllOperatorDialogs(String operator);

  List<DialogDTO> getAllCustomerDialogs(String customerId);
  
  List<DialogDTO> getAllDialogsForDate(String dateStart, String dateEnd);

  List<DialogDTO> getAllOperatorDialogsForDate(String operator, String dateStart, String dateEnd);

  List<DialogDTO> getAllCustomerDialogsForDate(String customerId, String dateStart, String dateEnd);
  
  List<DialogDTO> getAllOperatorAndCustomerDialogs(String operator, String customerId);
  
  List<DialogDTO> getAllOperatorAndCustomerDialogsForDate(String operator, String customerId, String dateStart, String dateEnd);

  List<DialogDTO> findByWord(String text);

}
