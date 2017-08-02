package com.oblenergo.chat.dao;

import java.util.Date;
import java.util.List;

import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;

public interface DialogDao {

  public void findAndPushMessage(String dialog_id, Message m);

//  List<Dialog> findByCustomerIdAndDate(String customerId, Date date, Date nextDay);
//
//  List<Dialog> findByOperatorAndDate(String operator, Date date, Date nextDay);

}
