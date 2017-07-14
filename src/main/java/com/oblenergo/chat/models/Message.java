package com.oblenergo.chat.models;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document
public class Message implements Serializable {

  private static final long serialVersionUID = -938812457055856020L;

  @Field
  private String date;
  @Field
  private String time;
  @Field
  private String operator_login;
  @Field
  private String text;

}
