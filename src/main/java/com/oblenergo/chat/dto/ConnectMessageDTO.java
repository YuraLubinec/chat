package com.oblenergo.chat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectMessageDTO implements Serializable {

  private static final long serialVersionUID = -868419736579442992L;

  private String id;
  private String dialog_id;
}
