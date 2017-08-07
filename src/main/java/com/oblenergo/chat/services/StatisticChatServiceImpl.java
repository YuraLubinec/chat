package com.oblenergo.chat.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.dao.StatisticChatDao;
import com.oblenergo.chat.dto.StatisticChatDTO;

@Service
public class StatisticChatServiceImpl implements StatisticChatService {

	@Autowired
	StatisticChatDao statChatRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	private static final String DATEPATTERN = "yyyy-MM-dd";
	
	// convert date from String to Date
	private Date convertStringToDate(String dateToConvert){
		SimpleDateFormat sdf = new SimpleDateFormat(DATEPATTERN);
		Date convDate = null;
		try {
			convDate = sdf.parse(dateToConvert);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convDate;
	}
	
	// common by all
	@Override
	public StatisticChatDTO getCountByAllOperatorsByDate(String dateFrom, String dateTo) {
		
		Date dBeg = convertStringToDate(dateFrom);
		Date dEnd = convertStringToDate(dateTo);
		
		StatisticChatDTO dto = new StatisticChatDTO();
		dto.setCountAll(statChatRepository.getCountAll(dBeg, dEnd));				
		dto.setCountHandled(statChatRepository.getHandledCommon(dBeg, dEnd));
		dto.setCountLost(statChatRepository.getLostCommon(dBeg, dEnd));
		
		dto.setAverageHoldTime(statChatRepository.getAverageCommonHoldTime(dBeg, dEnd));				
		dto.setAverageRate(statChatRepository.getAverageCommonRate(dBeg, dEnd));
		
		return dto;
	}

	// by operators
	@Override
	public StatisticChatDTO getCountAllByOperatorByDate(String operator, String dateFrom, String dateTo) {
		Date dBeg = convertStringToDate(dateFrom);
		Date dEnd = convertStringToDate(dateTo);
		
		StatisticChatDTO dto = new StatisticChatDTO();
		dto.setCountAll(statChatRepository.getCountAllByOperator(operator, dBeg, dEnd));				
		dto.setCountHandled(statChatRepository.getHandledByOperator(operator, dBeg, dEnd));
		dto.setCountLost(statChatRepository.getLostByOperator(operator, dBeg, dEnd));
		dto.setAverageHoldTime(statChatRepository.getAverageHoldTimeByOperator(operator, dBeg, dEnd));
		dto.setAverageRate(statChatRepository.getAverageRateByOperator(operator, dBeg, dEnd));
		
		return dto;
	}
	
	// by customers
	@Override
	public StatisticChatDTO getCountAllByCustomerIdByDate(String customer, String dateFrom, String dateTo) {
		Date dBeg = convertStringToDate(dateFrom);
		Date dEnd = convertStringToDate(dateTo);
		
		StatisticChatDTO dto = new StatisticChatDTO();
		dto.setCountAll(statChatRepository.getCountAllByCustomerId(customer, dBeg, dEnd));				
		dto.setCountHandled(statChatRepository.getHandledByCustomerId(customer, dBeg, dEnd));
		dto.setCountLost(statChatRepository.getLostByCustomerId(customer, dBeg, dEnd));
		dto.setAverageHoldTime(statChatRepository.getAverageHoldTimeByCustomer(customer, dBeg, dEnd));
		dto.setAverageRate(statChatRepository.getAverageRateByCustomer(customer, dBeg, dEnd));
		
		return dto;
	}
	
	/*
	 * 
	 */
	
	@Override
	public Map<String, StatisticChatDTO> getOperatorStatListByDate(String dateFrom, String dateTo) {
		Date dBeg = convertStringToDate(dateFrom);
		Date dEnd = convertStringToDate(dateTo);
		
		// find operator from DB 
		List<String> listOp = statChatRepository.findAllDistinctOperators(dBeg, dEnd);

		Map<String, StatisticChatDTO> mapListOperator = new HashMap<String, StatisticChatDTO>();
		
		for(String operator : listOp){
			StatisticChatDTO dto = new StatisticChatDTO();
			dto.setCountAll(statChatRepository.getCountAllByOperator(operator, dBeg, dEnd));				
			dto.setCountHandled(statChatRepository.getHandledByOperator(operator, dBeg, dEnd));
			dto.setCountLost(statChatRepository.getLostByOperator(operator, dBeg, dEnd));		
			dto.setAverageHoldTime(statChatRepository.getAverageHoldTimeByOperator(operator, dBeg, dEnd));				
			dto.setAverageRate(statChatRepository.getAverageRateByOperator(operator, dBeg, dEnd));

			mapListOperator.put(operator, dto);
		}
				
		return mapListOperator;
	}

	@Override
	public Map<String, StatisticChatDTO> getCustomerStatListByDate(String dateFrom, String dateTo) {		
		Date dBeg = convertStringToDate(dateFrom);
		Date dEnd = convertStringToDate(dateTo);
		
		// find customer from DB 
		List<String> listCust = statChatRepository.findAllDistinctCustomers(dBeg, dEnd);

		Map<String, StatisticChatDTO> mapListCustomer = new HashMap<String, StatisticChatDTO>();
		
		for(String customer : listCust){
			StatisticChatDTO dto = new StatisticChatDTO();
			dto.setCountAll(statChatRepository.getCountAllByCustomerId(customer, dBeg, dEnd));				
			dto.setCountHandled(statChatRepository.getHandledByCustomerId(customer, dBeg, dEnd));
			dto.setCountLost(statChatRepository.getLostByCustomerId(customer, dBeg, dEnd));		
			dto.setAverageHoldTime(statChatRepository.getAverageHoldTimeByCustomer(customer, dBeg, dEnd));				
			dto.setAverageRate(statChatRepository.getAverageRateByCustomer(customer, dBeg, dEnd));

			mapListCustomer.put(customer, dto);
		}		
		
		return mapListCustomer;
	}
	
}
