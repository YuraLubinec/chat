package com.oblenergo.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.DialogDTO;
import com.oblenergo.chat.services.SearchService;

@RestController
@RequestMapping("/admin")
public class AdminController {
  
  @Autowired
  private SearchService searchService;
  
  @GetMapping("/dialog/operator/{operator_identifier}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllOperatorDialogs(@PathVariable String operator_identifier){
   
    return searchService.getAllOperatorDialogs(operator_identifier);
  }
  
  @GetMapping("/dialog/customer/{customer_id}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerDialogs(@PathVariable String customer_id){
   
    return searchService.getAllCustomerDialogs(customer_id);
  }
  
  @GetMapping("/dialog/operator/{operator_identifier}/{dateStart}/{dateEnd}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllOperatorDialogsForDate(@PathVariable String operator_identifier, @PathVariable String dateStart, @PathVariable String dateEnd){
   
    return searchService.getAllOperatorDialogsForDate(operator_identifier, dateStart, dateEnd);
  }
  
  @GetMapping("/dialog/customer/{customer_id}/{dateStart}/{dateEnd}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerDialogsForDate(@PathVariable String customer_id, @PathVariable String dateStart, @PathVariable String dateEnd){
   
    return searchService.getAllCustomerDialogsForDate(customer_id, dateStart, dateEnd);
  }
  
  @GetMapping("/dialog/date/{dateStart}/{dateEnd}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllForDate(@PathVariable String dateStart, @PathVariable String dateEnd){
    
    return searchService.getAllDialogsForDate(dateStart, dateEnd);
  }
  
  @GetMapping("/dialog/{customer_id}/{operator}/{dateStart}/{dateEnd}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerAndOperatorDialogsForDate(@PathVariable String customer_id, @PathVariable String operator, @PathVariable String dateStart, @PathVariable String dateEnd){
   
    return searchService.getAllOperatorAndCustomerDialogsForDate(operator, customer_id, dateStart, dateEnd);
  }
  
  @GetMapping("/dialog/{customer_id}/{operator}")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllCustomerAndOperatorDialogs(@PathVariable String customer_id, @PathVariable String operator){
   
    return searchService.getAllOperatorAndCustomerDialogs(operator, customer_id);
  }
  
  @GetMapping("/dialog/text")
  @ResponseStatus(HttpStatus.OK)
  public List<DialogDTO> getAllDialogsWithWord(@RequestParam String text){

    return searchService.findByWord(text);
  }

}
