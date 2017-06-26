package com.oblenergo.chat.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oblenergo.chat.enums.Reasons;

@Service
public class BillServiceImpl implements BillService {

	private static final String REGEXPATTERN = "amountToPay=\"[+-]?(\\d+)([\\.]?)(\\d*)";
	private static final int LENTH_TO_TRIM = 13;
	
	@Autowired
  private StatisticService statisticService;

	@Value("${bill.service.url}")
	private String url;

	@Value("${xmlToSendFirstPart}")
	private String xmlToSendFirstPart;

	@Value("${xmlToSendSecondPart}")
	private String xmlToSendSecondPart;

	@Override
	public ResponseEntity<String> getBill(String accountNumber) {
	 
	  RestTemplate template = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(xmlToSendFirstPart + accountNumber + xmlToSendSecondPart);
		statisticService.saveStatisticForPhysCustomer(accountNumber, Reasons.BILL);
		return new ResponseEntity<String>(parseResponse(template.postForObject(url, request, String.class)),
				HttpStatus.OK);
	}

	private String parseResponse(String response) {

		Pattern p = Pattern.compile(REGEXPATTERN);
		Matcher m = p.matcher(response);
		String token = null;
		while (m.find()) {
			token = m.group().substring(LENTH_TO_TRIM);
		}
		return token;
	}

}
