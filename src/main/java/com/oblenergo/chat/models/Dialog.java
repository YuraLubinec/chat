package com.oblenergo.chat.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection="dialogs")
public class Dialog implements Serializable{
  
  private static final long serialVersionUID = -8155879275429306631L;

  @Id
  private String id;
  @Field
  private String date;
  @Field
  private String customer_id;
  @Field
  private String operator;
  @Field
  private String holdTime;
  @Field
  private int rate;
  @Field
  private List<Message> messages;
  @Field
  private List<String> redirectTo;
}
