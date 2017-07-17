package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleDTO implements Serializable {

  private static final long serialVersionUID = -4814001917955689347L;
  
  private String identifier;
  private String value;

  public RoleDTO() {
  }

  public RoleDTO(String identifier, String value) {
    this.identifier = identifier;
    this.value = value;
  }
}
