package com.oblenergo.chat.enums;

public enum Roles {
  
  ADMIN("адміністратор"), OPERATOR("оператор");
  
  private String role;
  
  private Roles(String role){
    this.role = role;
  }
  
  public String getRole(){
    return this.role;
  }

}
