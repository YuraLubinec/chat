package com.oblenergo.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oblenergo.chat.models.TurnOffReportPhys;

public interface TurnOffReportPhysRepository extends JpaRepository<TurnOffReportPhys, Long> {
  
  TurnOffReportPhys findTopByAccountNumber(String accountNumber);

}
