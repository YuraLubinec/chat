package com.oblenergo.chat.enums;

public enum Scenario {
  PLANNED_WORK("Планові роботи"),
  EMERGENCY_WORK("Аварійні роботи");

  private String scenarioName;

  private Scenario(String scenarioName) {
    this.scenarioName = scenarioName;
  }

  public String getScenarioName() {
    return scenarioName;
  }

}
