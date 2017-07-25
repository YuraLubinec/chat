package com.oblenergo.chat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.models.Message;

@Repository
public class DialogDaoImpl implements DialogDao {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public void findAndPushMessage(String dialog_id, Message m) {
    mongoTemplate.findAndModify(
        new Query().addCriteria(Criteria.where("id").is(dialog_id)), new Update().push("messages", m), Dialog.class);
    
  }
}
