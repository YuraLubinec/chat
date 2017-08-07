package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class IndicatorDTO implements Serializable {

  private static final long serialVersionUID = -8008185190316146558L;

  private String accountNumber;
  private String counterValue;
  private String phoneNumber;  

}
