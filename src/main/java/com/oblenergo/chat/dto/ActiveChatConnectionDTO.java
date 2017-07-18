package com.oblenergo.chat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveChatConnectionDTO implements Serializable {

  private static final long serialVersionUID = -5005275104242548981L;
  
  private String customerId;
}