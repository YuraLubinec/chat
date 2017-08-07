package com.oblenergo.chat.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dao.StatisticChatDao;
import com.oblenergo.chat.dto.StatisticChatDTO;

import lombok.SneakyThrows;

@Service
public class StatisticChatServiceImpl implements StatisticChatService {

  @Autowired
  StatisticChatDao statisticChatDao;

  private static final String DATEPATTERN = "yyyy-MM-dd";

  @Override
  public StatisticChatDTO getGeneralStatisticForDate(String dateFrom, String dateTo) {

    return statisticChatDao.getGeneralStatisticForDate(convertStringToDate(dateFrom), convertStringToDate(dateTo));
  }

  @Override
  public StatisticChatDTO getOperatorStatisticForDate(String operator, String dateFrom, String dateTo) {

    return statisticChatDao.getOperatorStatisticForDate(operator, convertStringToDate(dateFrom), convertStringToDate(dateTo));
  }

  @Override
  public List<StatisticChatDTO> getAllOperatorsStatisticForDate(String dateBeg, String dateEnd) {

    return statisticChatDao.getAllOperatorsStatisticForDate(convertStringToDate(dateBeg), convertStringToDate(dateEnd));
  }

  @Override
  public StatisticChatDTO getCustomerStatisticForDate(String customerid, String dateBeg, String dateEnd) {

    return statisticChatDao.getCustomerStatisticForDate(customerid, convertStringToDate(dateBeg), convertStringToDate(dateEnd));

  }

  @Override
  public List<StatisticChatDTO> getAllCustomersStatisticForDate(String dateBeg, String dateEnd) {

    return statisticChatDao.getAllCustomersStatisticForDate(convertStringToDate(dateBeg), convertStringToDate(dateEnd));
  }

  // convert date from String to Date
  @SneakyThrows
  private Date convertStringToDate(String dateToConvert) {

    SimpleDateFormat sdf = new SimpleDateFormat(DATEPATTERN);
    return sdf.parse(dateToConvert);
  }

}
