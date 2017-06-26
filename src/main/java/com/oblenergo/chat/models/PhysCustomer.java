package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dbo.PhysPersonCheck")
public class PhysCustomer implements Serializable {

  private static final long serialVersionUID = -621667797282692035L;

  @Id
  @Column(name = "id")
  private long id;
  @Column(name = "accountNumber")
  private String accountNumber;
  @Column(name = "activeScaleCount")
  private Integer activeScaleCount;

}
