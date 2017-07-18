package com.oblenergo.chat.dao;

import com.oblenergo.chat.models.Message;

public interface DialogDao {

  public void findAndModifyOperatorName(String dialog_id, String id);

  public void findAndPushMessage(String dialog_id, Message m);

}