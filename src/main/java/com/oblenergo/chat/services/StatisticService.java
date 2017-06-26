package com.oblenergo.chat.services;

import com.oblenergo.chat.enums.Reasons;

public interface StatisticService {

  void saveStatisticForPhysCustomer(String accountNumber, Reasons reason);
  
  void saveStatisticForJurCustomer(String contractNumber, Reasons reason);

}
