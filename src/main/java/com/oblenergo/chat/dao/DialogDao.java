package com.oblenergo.chat.dao;

import java.util.List;

import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;

public interface DialogDao {

  void findAndPushMessage(String dialog_id, Message m);

  List<Dialog> findAllByWord(String text);

  void findAndPushRate(String dialog_id, int rate);

}
