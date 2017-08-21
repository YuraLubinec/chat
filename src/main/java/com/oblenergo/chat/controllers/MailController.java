package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.oblenergo.chat.dto.MailSendDTO;
import com.oblenergo.chat.services.MailServiceImpl;

@Controller
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailController {

  @Autowired
  MailServiceImpl mailService;

  @PostMapping("/complaint")
  @ResponseStatus(HttpStatus.OK)
  public void mailSendController(@RequestBody MailSendDTO complaint) {

    System.out.println(
        "Service mail : " + complaint.getClientId() + "  " + complaint.getText() + " email : " + complaint.getEmail());
    mailService.sendMail(complaint.getClientId(), complaint.getText(), complaint.getEmail());
  }

}
