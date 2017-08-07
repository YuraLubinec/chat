package com.oblenergo.chat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorResponseDTO implements Serializable {

  private static final long serialVersionUID = 490334213732184514L;

  private String answer;

}
