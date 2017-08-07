package com.oblenergo.chat.enums;

public enum Reasons {

  INDICATOR("indicator"), BILL("bill"), CONSULTATION("consultation"), NOENERGYREPORTJUR(
      "reportJur"), NOENERGYREPORTPHYS("reportPhys");

  private String reason;

  private Reasons(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }

}
