package com.oblenergo.chat.services;

import java.util.List;

import com.oblenergo.chat.dto.StatisticChatDTO;

public interface StatisticChatService {

  StatisticChatDTO getGeneralStatisticForDate(String dateFrom, String dateTo);

  StatisticChatDTO getOperatorStatisticForDate(String operator, String dateFrom, String dateTo);

  List<StatisticChatDTO> getAllOperatorsStatisticForDate(String dateBeg, String dateEnd);

  StatisticChatDTO getCustomerStatisticForDate(String customerid, String dateBeg, String dateEnd);

  List<StatisticChatDTO> getAllCustomersStatisticForDate(String dateBeg, String dateEnd);
}
