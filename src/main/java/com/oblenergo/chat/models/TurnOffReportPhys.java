package com.oblenergo.chat.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dbo.PhysicalCustomerturnOffInfo")
public class TurnOffReportPhys implements Serializable {

  private static final long serialVersionUID = 7897348277407699052L;

  @Id
  @Column(name = "accountNumber")
  private String accountNumber;
  @Column(name = "scenarioId")
  private int scenarioId;
  @Column(name = "endTurnOff")
  private String endTurnOff;
  @Column(name = "fullAddress")
  private String fullAddress;

}
