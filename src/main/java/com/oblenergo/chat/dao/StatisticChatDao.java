package com.oblenergo.chat.dao;

import java.util.Date;
import java.util.List;

public interface StatisticChatDao {

	//5 methods to find common data
	long getCountAll(Date dateFrom, Date dateTo);
	long getLostCommon(Date dateFrom, Date dateTo);
	long getHandledCommon(Date dateFrom, Date dateTo);
	double getAverageCommonHoldTime(Date dateFrom, Date dateTo);
	double getAverageCommonRate(Date dateFrom, Date dateTo); 
	
	//5 methods to find data for operator
	long getCountAllByOperator(String operator, Date dateFrom, Date dateTo);
	long getLostByOperator(String operator, Date dateFrom, Date dateTo);
	long getHandledByOperator(String operator, Date dateFrom, Date dateTo);
	double getAverageHoldTimeByOperator(String operator, Date dateFrom, Date dateTo);
	double getAverageRateByOperator(String operator, Date dateFrom, Date dateTo);
	
	//5 methods to find data for customer
	long getCountAllByCustomerId(String customer, Date dateFrom, Date dateTo);
	long getLostByCustomerId(String customer, Date dateFrom, Date dateTo);
	long getHandledByCustomerId(String customer, Date dateFrom, Date dateTo);
	double getAverageHoldTimeByCustomer(String customer, Date dateFrom, Date dateTo);
	double getAverageRateByCustomer(String customer, Date dateFrom, Date dateTo);
	
	List<String> findAllDistinctOperators(Date dateFrom, Date dateTo);
	
	List<String> findAllDistinctCustomers(Date dBeg, Date dEnd);
	
}
