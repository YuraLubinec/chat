package com.oblenergo.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oblenergo.chat.models.JuridicalCustomer;

@Transactional(readOnly=true)
public interface JuridicalCustomerRepository extends JpaRepository<JuridicalCustomer, Long> {
  
  JuridicalCustomer findTopByContractNumber(String contractNumber);

}
