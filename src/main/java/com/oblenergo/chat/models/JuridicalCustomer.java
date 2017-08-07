package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "billing.JurPersonBind")
public class JuridicalCustomer implements Serializable {

  private static final long serialVersionUID = -1524782270575742464L;

  @Id
  @Column(name = "id")
  private int id;
  @Column(name = "contractNumber")
  private String contractNumber;
}
