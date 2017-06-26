package com.oblenergo.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.JuridicalIdentifier;
import com.oblenergo.chat.dto.TurnOffReportDTO;
import com.oblenergo.chat.models.JuridicalCustomer;
import com.oblenergo.chat.repositories.JuridicalCustomerRepository;
import com.oblenergo.chat.services.ReportService;

@RestController
@RequestMapping("/customer/juridical")
public class JuridicalCustomerController {

  @Autowired
  private JuridicalCustomerRepository juridicalCustomerRepository;
  
  @Autowired
  private ReportService reportService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public JuridicalCustomer JuridicalCustomercheckContractNumber(@Validated @RequestBody JuridicalIdentifier identifier) {
 
    return juridicalCustomerRepository.findTopByContractNumber(identifier.getContractNumber());
  }

  @PostMapping("/report")
  @ResponseStatus(HttpStatus.OK)
  public List<TurnOffReportDTO> getEnergyReport(@Validated @RequestBody JuridicalIdentifier identifier) {

    return reportService.getTurnOffReportjJur(identifier.getContractNumber());
  }

}
