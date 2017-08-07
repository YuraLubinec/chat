package com.oblenergo.chat.services;

import java.util.List;

import com.oblenergo.chat.dto.TurnOffReportDTO;

public interface ReportService {
  
  TurnOffReportDTO getTurnOffReportPhys(String accountNumber);
  
  List<TurnOffReportDTO> getTurnOffReportjJur(String contractNumber);
}
