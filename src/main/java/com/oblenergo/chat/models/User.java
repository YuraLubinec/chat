package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.oblenergo.chat.enums.Roles;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 3863084468437626199L;

  @Id
  private String id;
  @Field
  @NotBlank
  @Size(min=4, max=15)
  private String username;
  @Field
  @NotBlank
  @Size(min=8, max=15)
  private String password;
  @Field 
  @NotBlank
  @Size(max=20)
  private String fullName;
  
  @Field
  @Enumerated(EnumType.STRING)
  @NotNull
  private Roles role;

}
