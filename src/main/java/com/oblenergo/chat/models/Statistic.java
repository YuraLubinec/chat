package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oblenergo.chat.enums.Reasons;

import lombok.Data;

@Data
@Entity
@Table(name = "callcenter.chatbotStatistic")
public class Statistic implements Serializable {

  private static final long serialVersionUID = -3653936311706121639L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "date")
  private String date;
  @Column(name = "reason")
  @Enumerated(EnumType.STRING)
  private Reasons reason;
  @Column(name = "accountNumber")
  private String accountNumber;
  @Column(name = "contractNumber")
  private String contractNumber;

}
