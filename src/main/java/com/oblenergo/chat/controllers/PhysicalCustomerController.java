package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.IndicatorOneZoneDTO;
import com.oblenergo.chat.dto.IndicatorThreeZoneDTO;
import com.oblenergo.chat.dto.IndicatorTwoZoneDTO;
import com.oblenergo.chat.dto.TurnOffReportDTO;
import com.oblenergo.chat.models.PhysCustomer;
import com.oblenergo.chat.repositories.PhysCustomerRepository;
import com.oblenergo.chat.services.BillService;
import com.oblenergo.chat.services.IndicatorService;
import com.oblenergo.chat.services.ReportService;

@RestController
@RequestMapping("/customer/physical/{accountNumber}")
public class PhysicalCustomerController {

  @Autowired
  private PhysCustomerRepository physCustomerRepository;

  @Autowired
  private ReportService reportService;

  @Autowired
  private IndicatorService indicatorService;

  @Autowired
  private BillService billService;
  
  @GetMapping
  public PhysCustomer checkId(@PathVariable String accountNumber) {

    return physCustomerRepository.findByAccountNumber(accountNumber);
  }

  @GetMapping("/report")
  public TurnOffReportDTO getEnergyReport(@PathVariable String accountNumber) {

    return reportService.getTurnOffReportPhys(accountNumber);
  }

  @GetMapping("/bill")
  public ResponseEntity<String> getBillForPhysicalCustomer(@PathVariable String accountNumber) {

    return billService.getBill(accountNumber);
  }

  @PostMapping("/indicator/onezone")
  public ResponseEntity<String> saveIndicatorForOneZoneCounter(@PathVariable String accountNumber, @Validated @RequestBody IndicatorOneZoneDTO oneZoneDTO) {

    return indicatorService.saveOneZoneIndicator(oneZoneDTO, accountNumber);
  }

  @PostMapping("/indicator/twozone")
  public ResponseEntity<String> saveIndicatorForTwoZoneCounter(@PathVariable String accountNumber, @Validated @RequestBody IndicatorTwoZoneDTO twoZoneDTO) {

    return indicatorService.saveTwoZoneIndicator(twoZoneDTO, accountNumber);
  }

  @PostMapping("/indicator/threezone")
  public ResponseEntity<String> saveIndicatorForThreeZoneCounter(@PathVariable String accountNumber,
      @Validated @RequestBody IndicatorThreeZoneDTO threeZoneDTO) {

    return indicatorService.saveThreeZoneIndicator(threeZoneDTO, accountNumber);
  }

}
