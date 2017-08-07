package com.oblenergo.chat.dao;

import java.util.Date;
import java.util.List;

import com.oblenergo.chat.dto.StatisticChatDTO;

public interface StatisticChatDao {

  List<StatisticChatDTO> getAllOperatorsStatisticForDate(Date dateFrom, Date dateTo);

  StatisticChatDTO getGeneralStatisticForDate(Date dateFrom, Date dateTo);

  StatisticChatDTO getOperatorStatisticForDate(String operator, Date dateFrom, Date dateTo);

  StatisticChatDTO getCustomerStatisticForDate(String consumer, Date dateFrom, Date dateTo);

  List<StatisticChatDTO> getAllCustomersStatisticForDate(Date dateFrom, Date dateTo);

}
