package com.oblenergo.chat.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oblenergo.chat.models.Dialog;

public interface DialogRepository extends MongoRepository<Dialog, String>{
  
}
