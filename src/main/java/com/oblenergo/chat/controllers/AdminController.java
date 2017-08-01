package com.oblenergo.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.DialogDTO;
import com.oblenergo.chat.services.DialogService;

@RestController
@RequestMapping("/admin")
public class AdminController {
  
  @Autowired
  private DialogService dialogService;
  
  @GetMapping("/operator/{operator_identifier}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllOperatorDialogs(@PathVariable String operator_identifier){
   
    return dialogService.getAllOperatorDialogs(operator_identifier);
  }
  
  @GetMapping("/customer/{customer_id}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerDialogs(@PathVariable String customer_id){
   
    return dialogService.getAllCustomerDialogs(customer_id);
  }
  
  @GetMapping("/operator/{operator_identifier}/{date}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllOperatorDialogsForDate(@PathVariable String operator_identifier, @PathVariable String date){
   
    return dialogService.getAllOperatorDialogsForDate(operator_identifier, date);
  }
  
  @GetMapping("/customer/{customer_id}/{date}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerDialogsForDate(@PathVariable String customer_id, @PathVariable String date){
   
    return dialogService.getAllCustomerDialogsForDate(customer_id, date);
  }

}
