package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 3863084468437626199L;

  @Id
  private String id;
  @Field
  private String name;
  @Field
  private String password;
  @Field
  private String role;

}
