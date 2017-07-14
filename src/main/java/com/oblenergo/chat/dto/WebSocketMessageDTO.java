package com.oblenergo.chat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSocketMessageDTO implements Serializable {

  private static final long serialVersionUID = 3289563156710850600L;

  private String text;
  private String dialog_id;

}
