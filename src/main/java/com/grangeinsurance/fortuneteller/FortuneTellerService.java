package com.grangeinsurance.fortuneteller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class FortuneTellerService {

	@Value("${works}")
	private String works;
	
	@Value("${fortunesAPI}")
	String fortunesAPI;
	
	public String testMethod() {
		return fortunesAPI;
	}
	
	public static final String TELLER_SERVICE = "tellerService";
	
	@CircuitBreaker(name = TELLER_SERVICE, fallbackMethod = "getFallbackFortune")
	public String findMyFate() {
		
		RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(fortunesAPI, String.class);
		
		if (result != null) {
		    String[] fortuneData = result.replaceAll("[\\[\\](){}]", "").split(":");
		    return fortuneData[1];
		} else {
			return "You are fated to find out another day...";
		}
	    
	}
	
	public String getFallbackFortune(Exception e) {
		return "\"You've met with a terrible fate, haven't you?\"";
	}
}
