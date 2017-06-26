package com.oblenergo.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oblenergo.chat.models.JuridicalCustomer;

public interface JuridicalCustomerRepository extends JpaRepository<JuridicalCustomer, Long> {
  
  @Transactional(readOnly=true)
  JuridicalCustomer findTopByContractNumber(String contractNumber);

}
