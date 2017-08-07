package com.oblenergo.chat.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.oblenergo.chat.dto.StatisticChatDTO;
import com.oblenergo.chat.models.Dialog;
import com.oblenergo.chat.repositories.DialogRepository;

@Repository
public class StatisticChatDaoImpl implements StatisticChatDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	DialogRepository dialogRepository;

	// common	
	@Override
	public long getCountAll(Date dateFrom, Date dateTo) {
		return dialogRepository.countByDateBetween(dateFrom, dateTo);
	}

	@Override
	public long getLostCommon(Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("holdTime").lte(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public long getHandledCommon(Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("holdTime").gt(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public double getAverageCommonHoldTime(Date dateFrom, Date dateTo) {
		DBCollection collection = mongoTemplate.getCollection("dialogs");
	    // avarage for all
	    List<BasicDBObject> pipeline = Arrays.asList(
	    		new BasicDBObject("$match", new BasicDBObject("date", new BasicDBObject("$gte", dateFrom).append("$lte", dateTo))), 
	    		new BasicDBObject("$group", new BasicDBObject("_id", null).append("avgHoldtime",new BasicDBObject("$avg", "$holdTime"))));
	    
	    AggregationOutput result = collection.aggregate(pipeline);
		
	    double resultAvgHT = 0.0;    
	    for (DBObject object : result.results()) {
	    	resultAvgHT  = (double) object.get("avgHoldtime");
        }
		    
		return resultAvgHT;
	}

	@Override
	public double getAverageCommonRate(Date dateFrom, Date dateTo) {
		DBCollection collection = mongoTemplate.getCollection("dialogs");
	    // avarage for all
	    List<BasicDBObject> pipeline = Arrays.asList(
	    		new BasicDBObject("$match", new BasicDBObject("date", new BasicDBObject("$gte", dateFrom).append("$lte", dateTo))), 
	    		new BasicDBObject("$group", new BasicDBObject("_id", null).append("avgRate",new BasicDBObject("$avg", "$rate"))));
	    
	    AggregationOutput result = collection.aggregate(pipeline);
		
	    double resultAvgRate = 0.0;    
	    for (DBObject object : result.results()) {
	    	resultAvgRate  = (double) object.get("avgRate");
        }
		
		return resultAvgRate;
	}

	// by operator
	@Override
	public long getCountAllByOperator(String operator, Date dateFrom, Date dateTo) {
		return dialogRepository.countByOperatorAndDateBetween(operator, dateFrom, dateTo);
//				mongoTemplate.count(Query.query(Criteria.where("operator").is(operator).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public long getLostByOperator(String operator, Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("operator").is(operator).and("holdTime").lte(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public long getHandledByOperator(String operator, Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("operator").is(operator).and("holdTime").gt(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public double getAverageHoldTimeByOperator(String operator, Date dateFrom, Date dateTo) {
		
		Aggregation agg = newAggregation(
			match(Criteria.where("operator").is(operator).and("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo))),
			group("operator").avg("holdTime").as("averageHoldTime"),
			project("averageHoldTime").and("holdTime").previousOperation()
		);

		//Convert the aggregation result
		AggregationResults<StatisticChatDTO> groupResults = mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class);
		List<StatisticChatDTO> result = groupResults.getMappedResults();
		
		double avgHTOper= 0.0;
		List<StatisticChatDTO> listAvgOper = result;
		for(StatisticChatDTO obj : listAvgOper){
			avgHTOper = obj.getAverageHoldTime();
		}
		
		return avgHTOper;
	}

	@Override
	public double getAverageRateByOperator(String operator, Date dateFrom, Date dateTo) {
		
		Aggregation agg = newAggregation(
			match(Criteria.where("operator").is(operator).and("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo))),
			group("operator").avg("rate").as("averageRate"),
			project("averageRate").and("rate").previousOperation()
		);

		//Convert the aggregation result
		AggregationResults<StatisticChatDTO> groupResults = mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class);
		List<StatisticChatDTO> result = groupResults.getMappedResults();
		
		double avgRateOper= 0.0;
		List<StatisticChatDTO> listAvgRateOper = result;
		for(StatisticChatDTO obj : listAvgRateOper){
			avgRateOper = obj.getAverageRate();
		}
		
		return avgRateOper;
	}

	// by customer
	@Override
	public long getCountAllByCustomerId(String customer, Date dateFrom, Date dateTo) {
		return dialogRepository.countByCustomerIdAndDateBetween(customer, dateFrom, dateTo);
	}

	@Override
	public long getLostByCustomerId(String customer, Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("customerId").is(customer).and("holdTime").lte(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public long getHandledByCustomerId(String customer, Date dateFrom, Date dateTo) {
		return mongoTemplate.count(Query.query(Criteria.where("customerId").is(customer).and("holdTime").gt(0).and("date").gte(dateFrom).lte(dateTo)), Dialog.class);
	}

	@Override
	public double getAverageHoldTimeByCustomer(String customer, Date dateFrom, Date dateTo) {
		Aggregation agg = newAggregation(
			match(Criteria.where("customerId").is(customer).and("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo))),
			group("customerId").avg("holdTime").as("averageHoldTime"),
			project("averageHoldTime").and("holdTime").previousOperation()
		);

		//Convert the aggregation result
		AggregationResults<StatisticChatDTO> groupResults = mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class);
		List<StatisticChatDTO> result = groupResults.getMappedResults();
		
		double avgHTOper = 0.0;
		List<StatisticChatDTO> listAvgCust = result;
		for(StatisticChatDTO obj : listAvgCust){
			avgHTOper = obj.getAverageHoldTime();
		}
		
		return avgHTOper;
	}

	@Override
	public double getAverageRateByCustomer(String customer, Date dateFrom, Date dateTo) {
		Aggregation agg = newAggregation(
			match(Criteria.where("customerId").is(customer).and("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo))),
			group("customerId").avg("rate").as("averageRate"),
			project("averageRate").and("rate").previousOperation()
		);

		//Convert the aggregation result
		AggregationResults<StatisticChatDTO> groupResults = mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class);
		List<StatisticChatDTO> result = groupResults.getMappedResults();
		
		double avgRateCust= 0.0;
		List<StatisticChatDTO> listAvgRateCust = result;
		for(StatisticChatDTO obj : listAvgRateCust){
			avgRateCust = obj.getAverageRate();
		}

		return avgRateCust;
	}

	@Override
	public List<String> findAllDistinctOperators(Date dateFrom, Date dateTo) {
		Criteria criteria = Criteria.where("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo));
		Query query = new Query().addCriteria(criteria);
		List<String> list = mongoTemplate.getCollection("dialogs").distinct("operator",query.getQueryObject());
		return list;
	}

	@Override
	public List<String> findAllDistinctCustomers(Date dateFrom, Date dateTo) {
		Criteria criteria = Criteria.where("date").gte(dateFrom).andOperator(Criteria.where("date").lte(dateTo));
		Query query = new Query().addCriteria(criteria);
		List<String> list = mongoTemplate.getCollection("dialogs").distinct("customerId",query.getQueryObject());
		return list;
	}

}
