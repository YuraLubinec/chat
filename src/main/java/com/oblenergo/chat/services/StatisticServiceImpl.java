package com.oblenergo.chat.services;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.enums.Reasons;
import com.oblenergo.chat.models.Statistic;
import com.oblenergo.chat.repositories.JuridicalCustomerRepository;
import com.oblenergo.chat.repositories.PhysCustomerRepository;
import com.oblenergo.chat.repositories.StatisticRepository;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

  @Autowired
  private StatisticRepository statisticRepository;

  @Autowired
  private JuridicalCustomerRepository juridicalCustomerRepository;

  @Autowired
  private PhysCustomerRepository physCustomerRepository;

  @Async
  @Override
  public void saveStatisticForPhysCustomer(String accountNumber, Reasons reason) {

    if (physCustomerRepository.findByAccountNumber(accountNumber) != null) {
      Statistic statistic = new Statistic();
      statistic.setDate(LocalDateTime.now().toString());
      statistic.setAccountNumber(accountNumber);
      statistic.setReason(reason);
      statisticRepository.save(statistic);
    }
  }

  @Async
  @Override
  public void saveStatisticForJurCustomer(String contractNumber, Reasons reason) {

    if (juridicalCustomerRepository.findTopByContractNumber(contractNumber) != null) {
      Statistic statistic = new Statistic();
      statistic.setDate(LocalDateTime.now().toString());
      statistic.setContractNumber(contractNumber);
      statistic.setReason(reason);
      statisticRepository.save(statistic);
    }
  }

}
