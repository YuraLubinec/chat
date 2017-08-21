package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.oblenergo.chat.services.DialogService;

@Controller
public class OperatorRateController {

  @Autowired
  private DialogService dialogService;

  @RequestMapping(method = RequestMethod.POST, value = "/settingRate/{dialog_id}/{rate}")
  @ResponseStatus(HttpStatus.OK)
  public void setRateByClient(@PathVariable String dialog_id, @PathVariable int rate) {

    dialogService.setRateForOperator(dialog_id, rate);
  }

}
