package com.oblenergo.chat.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import com.oblenergo.chat.enums.Roles;

@Component
public class RoleEditor extends PropertyEditorSupport {
  
  @Override
  public void setAsText(String text) {
    this.setValue(Roles.valueOf(text));
  }

}
