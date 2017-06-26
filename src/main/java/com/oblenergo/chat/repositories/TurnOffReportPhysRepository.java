package com.oblenergo.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oblenergo.chat.models.TurnOffReportPhys;

public interface TurnOffReportPhysRepository extends JpaRepository<TurnOffReportPhys, Long> {
  
  @Transactional(readOnly=true)
  TurnOffReportPhys findTopByAccountNumber(String accountNumber);

}
