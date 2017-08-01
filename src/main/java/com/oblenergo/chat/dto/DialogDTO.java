package com.oblenergo.chat.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DialogDTO implements Serializable {

  private static final long serialVersionUID = 2440670781242853657L;
  
  private String id;
  private String date;
  private String customerId;
  private String operator;
  private long holdTime;
  private int rate;
  private List<MessageDTO> messages;
  private List<String> redirectTo;
  

}
