package com.oblenergo.chat.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorTwoZoneDTO implements Serializable {

  private static final long serialVersionUID = 411358646302415484L;

  @NotBlank
  @Pattern(regexp = "\\d+")
  @Size(max=8)
  private String dayIndicator;

  @NotBlank(message = "Не може бути пустим")
  @Pattern(regexp = "\\d+")
  @Size(max=8)
  private String nightIndicator;
  
  @Pattern(regexp = "(\\+380(\\d){9})|0(\\d){9}")
  private String phoneNumber;

}
