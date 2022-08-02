package com.grangeinsurance.fortuneteller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class FortuneTellerService {
	
	@Value("${fortunesAPI}")
	String fortunesAPI;
	
	public static final String TELLER_SERVICE = "tellerService";
	static final String DEFAULT_FORTUNE = "\"You've met with a terrible fate, haven't you?\"";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FortuneTellerService.class);
	
	@CircuitBreaker(name = TELLER_SERVICE, fallbackMethod = "getFallbackFortune")
	public String findMyFate() {
		
		RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(fortunesAPI, String.class);
		
		if (result != null) {
		    String[] fortuneData = result.replaceAll("[\\[\\](){}]", "").split(":");
		    return fortuneData[1];
		} else {
			return DEFAULT_FORTUNE;
		}
	    
	}
	
	public String getFallbackFortune(Exception e) {
		LOGGER.error("An exception was thrown!", e);
		return DEFAULT_FORTUNE;
	}
}
