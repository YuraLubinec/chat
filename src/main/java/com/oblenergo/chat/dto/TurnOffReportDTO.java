package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TurnOffReportDTO implements Serializable {

  private static final long serialVersionUID = 3926445125180580424L;
  
  private String scenario;
  private String endTurnOff;
  private String fullAddress;
}
