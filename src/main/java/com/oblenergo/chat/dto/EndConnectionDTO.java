package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EndConnectionDTO implements Serializable {
 
  private static final long serialVersionUID = -793951825049627572L;
  
  private boolean isEnded = true;

}
