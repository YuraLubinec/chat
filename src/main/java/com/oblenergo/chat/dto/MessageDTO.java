package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessageDTO implements Serializable {
  
  private static final long serialVersionUID = -7743531336612150727L;
  
  private String date;
  private String operator_login;
  private String text;

}
