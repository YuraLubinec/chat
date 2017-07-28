package com.oblenergo.chat.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.oblenergo.chat.dto.IndicatorDTO;
import com.oblenergo.chat.dto.IndicatorOneZoneDTO;
import com.oblenergo.chat.dto.IndicatorResponseDTO;
import com.oblenergo.chat.dto.IndicatorThreeZoneDTO;
import com.oblenergo.chat.dto.IndicatorTwoZoneDTO;
import com.oblenergo.chat.enums.Reasons;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IndicatorServiceImpl implements IndicatorService {

  private static final String DEFAULTERRORMESSAGE = "Показник внесено невірно, просимо уточнити показник";
  private static final  String DEFAULTFAILUREMESSAGE = "Сервіс недоступний, спробуйте пізніше";
  private static final String NOTAVAILABLEMESSAGE = "Доступно лише з 20 по 1 число";

  private static final int LAST_DAY_OF_MONTH_MAX_VALUE = 31;
  private static final int FIRST_DAY_OF_MONTH = 1;


  @Value("${indicator.service.url}")
  private String url;

  @Value("${begin.period.date}")
  private int beginPeriodDate;

  @Value("${end.period.date}")
  private int endPeriodDate;

  @Autowired
  private StatisticService statisticService;

  @Override
  public ResponseEntity<String> saveOneZoneIndicator(IndicatorOneZoneDTO oneZoneDTO, String accountNumber) {

    IndicatorDTO indicatorDTO = new IndicatorDTO();
    indicatorDTO.setAccountNumber(accountNumber);
    indicatorDTO.setCounterValue(oneZoneDTO.getIndicator());
    indicatorDTO.setPhoneNumber(oneZoneDTO.getPhoneNumber());
    return saveIndicator(indicatorDTO);
  }

  @Override
  public ResponseEntity<String> saveTwoZoneIndicator(IndicatorTwoZoneDTO twoZoneDTO, String accountNumber) {

    IndicatorDTO indicatorDTO = new IndicatorDTO();
    indicatorDTO.setAccountNumber(accountNumber);
    indicatorDTO.setCounterValue(twoZoneDTO.getDayIndicator() + "/" + twoZoneDTO.getNightIndicator());
    indicatorDTO.setPhoneNumber(twoZoneDTO.getPhoneNumber());
    return saveIndicator(indicatorDTO);
  }

  @Override
  public ResponseEntity<String> saveThreeZoneIndicator(IndicatorThreeZoneDTO threeZoneDTO, String accountNumber) {

    IndicatorDTO indicatorDTO = new IndicatorDTO();
    indicatorDTO.setAccountNumber(accountNumber);
    indicatorDTO.setCounterValue(threeZoneDTO.getPeakIndicator() + "/" + threeZoneDTO.getHalfPeakIndicator() + "/" + threeZoneDTO.getNightIndicator());
    indicatorDTO.setPhoneNumber(threeZoneDTO.getPhoneNumber());
    return saveIndicator(indicatorDTO);
  }

  private ResponseEntity<String> saveIndicator(IndicatorDTO indicator) {

    if (checkPeriod()) {
      IndicatorResponseDTO responseDTO = null;
      RestTemplate template = new RestTemplate();
      HttpEntity<IndicatorDTO> request = new HttpEntity<>(indicator);
      
      try {
        responseDTO = template.postForObject(url, request, IndicatorResponseDTO.class);
      } catch (RestClientException e) {
        log.error("Error occurred with indicator service " + e);
        return new ResponseEntity<String>(DEFAULTFAILUREMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if (responseDTO != null && responseDTO.getAnswer() != null) {
        statisticService.saveStatisticForPhysCustomer(indicator.getAccountNumber(), Reasons.INDICATOR);
        return new ResponseEntity<String>(responseDTO.getAnswer(), HttpStatus.OK);
      } else {
        return new ResponseEntity<String>(DEFAULTERRORMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<String>(NOTAVAILABLEMESSAGE, HttpStatus.OK);
    }
  }

  private boolean checkPeriod() {

    int dayOfMonth = LocalDate.now().getDayOfMonth();
    return dayOfMonth > beginPeriodDate && dayOfMonth <= LAST_DAY_OF_MONTH_MAX_VALUE || dayOfMonth < endPeriodDate && dayOfMonth >= FIRST_DAY_OF_MONTH;
  }

}
