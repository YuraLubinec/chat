package com.oblenergo.chat.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oblenergo.chat.models.Dialog;

public interface DialogRepository extends MongoRepository<Dialog, String> {

  Stream<Dialog> findByCustomerIdIgnoreCaseOrderByDateDesc(String customerId);

  Stream<Dialog> findByOperatorIgnoreCaseOrderByDateDesc(String operator);

  Stream<Dialog> findByCustomerIdAndDateAllIgnoreCaseOrderByDateDesc(String customerId, Date date);

  Stream<Dialog> findByOperatorAndDateAllIgnoreCaseOrderByDateDesc(String operator, Date date);
}
