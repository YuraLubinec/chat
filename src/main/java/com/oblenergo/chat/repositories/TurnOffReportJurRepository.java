package com.oblenergo.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oblenergo.chat.models.TurnOffReportJur;
import com.oblenergo.chat.models.TurnOffReportJurPK;

public interface TurnOffReportJurRepository extends JpaRepository<TurnOffReportJur, TurnOffReportJurPK>{
  
  List <TurnOffReportJur> findByContractNumber(String contractNumber);

}
