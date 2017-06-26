package com.oblenergo.chat.services;

import org.springframework.http.ResponseEntity;

import com.oblenergo.chat.dto.IndicatorOneZoneDTO;
import com.oblenergo.chat.dto.IndicatorThreeZoneDTO;
import com.oblenergo.chat.dto.IndicatorTwoZoneDTO;

public interface IndicatorService {

  ResponseEntity<String> saveOneZoneIndicator(IndicatorOneZoneDTO oneZoneDTO, String accountNumber);

  ResponseEntity<String> saveTwoZoneIndicator(IndicatorTwoZoneDTO twoZoneDTO, String accountNumber);

  ResponseEntity<String> saveThreeZoneIndicator(IndicatorThreeZoneDTO threeZoneDTO, String accountNumber);

}
