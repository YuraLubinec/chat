package com.oblenergo.chat.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.oblenergo.chat.dto.StatisticChatDTO;
import com.oblenergo.chat.models.Dialog;

@Repository
public class StatisticChatDaoImpl implements StatisticChatDao {

  @Autowired
  MongoTemplate mongoTemplate;

  /**
   *
   * Returns all customers statistic info
   * 
   * @param dateFrom
   *          - the start Date for statistic generation
   * @param dateTo
   *          - the end Date for statistic generation
   * @return List<StatisticChatDTO> with list of the statistics
   */
  @Override
  public List<StatisticChatDTO> getAllCustomersStatisticForDate(Date dateFrom, Date dateTo) {
    Criteria criteria = Criteria.where("date").gte(dateFrom).lte(dateTo);
    BasicDBObject group = new BasicDBObject("$group",
        new BasicDBObject("_id", "customerId").append("averageRate", new BasicDBObject("$avg", "$rate"))
            .append("averageHoldTime", new BasicDBObject("$avg", "$holdTime")).append("countAll", new BasicDBObject("$sum", 1))
            .append("countHandled",
                new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$ne", new Object[] { "$holdTime", 0 }), 1, 0 })))
            .append("countLost",
                new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$eq", new Object[] { "$holdTime", 0 }), 1, 0 }))));
    Aggregation agg = newAggregation(match(criteria), (context) -> context.getMappedObject(group));

    return mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class).getMappedResults();

  }

  /**
   *
   * Returns customers statistic info
   * 
   * @param customer
   *          - selected customer identifier
   * @param dateFrom
   *          - the start Date for statistic generation
   * @param dateTo
   *          - the end Date for statistic generation
   * @return StatisticChatDTO object with the statistic info
   */
  @Override
  public StatisticChatDTO getCustomerStatisticForDate(String customer, Date dateFrom, Date dateTo) {

    Criteria criteria = Criteria.where("date").gte(dateFrom).lte(dateTo).and("customerId").is(customer);
    BasicDBObject group = new BasicDBObject("$group",
        new BasicDBObject("_id", "customerId").append("averageRate", new BasicDBObject("$avg", "$rate"))
            .append("averageHoldTime", new BasicDBObject("$avg", "$holdTime")).append("countAll", new BasicDBObject("$sum", 1))
            .append("countHandled",
                new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$ne", new Object[] { "$holdTime", 0 }), 1, 0 })))
            .append("countLost",
                new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$eq", new Object[] { "$holdTime", 0 }), 1, 0 }))));
    Aggregation agg = newAggregation(match(criteria), (context) -> context.getMappedObject(group));

    return mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class).getUniqueMappedResult();
  }

  /**
   *
   * Returns operators statistic info
   * 
   * @param operator
   *          - selected operator login
   * @param dateFrom
   *          - the start Date for statistic generation
   * @param dateTo
   *          - the end Date for statistic generation
   * @return StatisticChatDTO object with the statistic info
   */
  @Override
  public StatisticChatDTO getOperatorStatisticForDate(String operator, Date dateFrom, Date dateTo) {

    Criteria criteria = Criteria.where("date").gte(dateFrom).lte(dateTo).and("operator").is(operator);
    Aggregation agg = newAggregation(match(criteria),
        group("operator").avg("holdTime").as("averageHoldTime").avg("rate").as("averageRate").count().as("countAll"));
    return mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class).getUniqueMappedResult();

  }

  /**
   *
   * Returns all operators statistic info
   * 
   * @param dateFrom
   *          - the start Date for statistic generation
   * @param dateTo
   *          - the end Date for statistic generation
   * @return List<StatisticChatDTO> with list of the statistics
   */

  @Override
  public List<StatisticChatDTO> getAllOperatorsStatisticForDate(Date dateFrom, Date dateTo) {

    Criteria criteria = Criteria.where("date").gte(dateFrom).lte(dateTo).andOperator(Criteria.where("operator").exists(true));
    Aggregation agg = newAggregation(match(criteria),
        group("operator").avg("holdTime").as("averageHoldTime").avg("rate").as("averageRate").count().as("countAll"));
    return mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class).getMappedResults();

  }

  /**
   *
   * Returns most common statistic info
   * 
   * @param dateFrom
   *          - the start Date for statistic generation
   * @param dateTo
   *          - the end Date for statistic generation
   * @return StatisticChatDTO object with the statistic info
   */
  @Override
  public StatisticChatDTO getGeneralStatisticForDate(Date dateFrom, Date dateTo) {

    Criteria criteria = Criteria.where("date").gte(dateFrom).lte(dateTo);
    BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "null").append("averageRate", new BasicDBObject("$avg", "$rate"))
        .append("averageHoldTime", new BasicDBObject("$avg", "$holdTime")).append("countAll", new BasicDBObject("$sum", 1))
        .append("countHandled",
            new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$ne", new Object[] { "$holdTime", 0 }), 1, 0 })))
        .append("countLost",
            new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { new BasicDBObject("$eq", new Object[] { "$holdTime", 0 }), 1, 0 }))));
    Aggregation agg = newAggregation(match(criteria), (context) -> context.getMappedObject(group));
    return mongoTemplate.aggregate(agg, Dialog.class, StatisticChatDTO.class).getUniqueMappedResult();
  }

}
