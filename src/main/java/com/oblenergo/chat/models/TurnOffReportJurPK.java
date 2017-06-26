package com.oblenergo.chat.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class TurnOffReportJurPK implements Serializable {

  private static final long serialVersionUID = -7293136604164882431L;
  
  private String contractNumber;
  private String fullAddress;

}
