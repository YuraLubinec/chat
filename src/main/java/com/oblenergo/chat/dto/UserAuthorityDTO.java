package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserAuthorityDTO implements Serializable{

  private static final long serialVersionUID = 1976315881625158865L;
  
  private String username;
  private String role;

}
