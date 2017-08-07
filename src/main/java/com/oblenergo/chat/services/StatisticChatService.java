package com.oblenergo.chat.services;

import java.util.Map;

import com.oblenergo.chat.dto.StatisticChatDTO;

public interface StatisticChatService {
	
	// common by all
	StatisticChatDTO getCountByAllOperatorsByDate(String dateFrom, String dateTo);
	
	// operator
	StatisticChatDTO getCountAllByOperatorByDate(String operator, String dateFrom, String dateTo);
	
	// operator list stat
	Map<String, StatisticChatDTO> getOperatorStatListByDate(String dateBeg, String dateEnd);

	// customer
	StatisticChatDTO getCountAllByCustomerIdByDate(String customerid, String dateBeg, String dateEnd);
	
	// customer list stat
	Map<String, StatisticChatDTO> getCustomerStatListByDate(String dateBeg, String dateEnd);	
}
