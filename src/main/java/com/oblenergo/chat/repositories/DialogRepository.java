package com.oblenergo.chat.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oblenergo.chat.models.Dialog;

public interface DialogRepository extends MongoRepository<Dialog, String> {

  Stream<Dialog> findByCustomerIdIgnoreCaseOrderByDateDesc(String customerId);

  Stream<Dialog> findByOperatorIgnoreCaseOrderByDateDesc(String operator);
  
  Stream<Dialog> findByDateBetweenOrderByDateDesc(Date date, Date nextDay);

  Stream<Dialog> findByCustomerIdIgnoreCaseAndDateBetweenOrderByDateDesc(String customerId, Date date, Date nextDay);

  Stream<Dialog> findByOperatorIgnoreCaseAndDateBetweenOrderByDateDesc(String operator, Date date, Date nextDay);
  
  Stream<Dialog> findByCustomerIdAndOperatorAllIgnoreCaseOrderByDateDesc(String customerId, String operator);
  
  Stream<Dialog> findByCustomerIdIgnoreCaseAndOperatorIgnoreCaseAndDateBetweenOrderByDateDesc(String operator, String customerId, Date date, Date nextDay);
  
}
