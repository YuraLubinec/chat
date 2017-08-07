package com.oblenergo.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StatisticChatDTO implements Serializable{
	
	private static final long serialVersionUID = 5895595558094105167L;
	
	private String _id;
	private long countAll;
	private long countLost;
	private long countHandled;
	private double averageHoldTime;
	private double averageRate;
	
}
