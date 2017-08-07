package com.oblenergo.chat.controllers;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.StatisticChatDTO;
import com.oblenergo.chat.services.StatisticChatService;

@RestController
@RequestMapping("/admin/statistic")
class StatisticChatController {
	@Autowired
	private StatisticChatService statisticChatService;
	
	@RequestMapping(method=RequestMethod.GET, value="/all/{dateBeg}_{dateEnd}")
	@ResponseStatus(HttpStatus.OK)
	public StatisticChatDTO getCommonStatisticByAllOparators(@PathVariable String dateBeg, @PathVariable String dateEnd) throws ParseException{
		return statisticChatService.getCountByAllOperatorsByDate(dateBeg, dateEnd);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/operator/{operatorid}/{dateBeg}_{dateEnd}")
	@ResponseStatus(HttpStatus.OK)
	public StatisticChatDTO getOperatorStatistic(@PathVariable String operatorid, @PathVariable String dateBeg, @PathVariable String dateEnd){
		return statisticChatService.getCountAllByOperatorByDate(operatorid, dateBeg, dateEnd);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/operator/operators_stat/{dateBeg}_{dateEnd}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, StatisticChatDTO> getOperatorListStatistic(@PathVariable String dateBeg, @PathVariable String dateEnd){
		return statisticChatService.getOperatorStatListByDate(dateBeg, dateEnd);
	}
	
	@RequestMapping("/customer/{customerid}/{dateBeg}_{dateEnd}")
	@ResponseStatus(HttpStatus.OK)
	public StatisticChatDTO getCustomerStatistic(@PathVariable String customerid, @PathVariable String dateBeg, @PathVariable String dateEnd){
		return statisticChatService.getCountAllByCustomerIdByDate(customerid, dateBeg, dateEnd);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/customer/customers_stat/{dateBeg}_{dateEnd}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, StatisticChatDTO> getCustomerListStatistic(@PathVariable String dateBeg, @PathVariable String dateEnd){
		return statisticChatService.getCustomerStatListByDate(dateBeg, dateEnd);
	}
	
}
