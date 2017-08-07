package com.oblenergo.chat.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorOneZoneDTO implements Serializable {

  private static final long serialVersionUID = -7178779695229018079L;
  
  @NotBlank
  @Pattern(regexp="\\d+")
  @Size(max=8)
  private String indicator;

  @Pattern(regexp = "(\\+380(\\d){9})|0(\\d){9}")
  private String phoneNumber;
}
