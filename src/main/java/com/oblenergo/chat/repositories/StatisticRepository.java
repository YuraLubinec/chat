package com.oblenergo.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oblenergo.chat.models.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
  
}
