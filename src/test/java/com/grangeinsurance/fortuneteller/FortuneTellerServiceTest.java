package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@SpringBootTest
class FortuneTellerServiceTest {

	@MockBean(name = "restTemplate")
	private RestTemplate restTemplate;
	
	private FortuneTellerService subject;
	
	@Value("${fortunesAPI}")
	String url;
	
	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;
	
	@BeforeEach
	void setup() {
		subject = new FortuneTellerService();
		circuitBreakerRegistry.circuitBreaker("tellerService").reset();
	}
	
	@Test
	void serviceReturnsRandomFortune() {
		ReflectionTestUtils.setField(subject, "fortunesAPI", "http://localhost:8080/fortunes/random");
		
		final String expected = "Test";
		
		String response = subject.findMyFate();
		
		assertThat(url).isEqualTo(subject.fortunesAPI);
	    assertThat(response.getClass().getSimpleName()).isEqualTo(expected.getClass().getSimpleName());
	}
	
	@Test
	void serviceReturnsDefaultWhenServiceIsUnavailable() throws URISyntaxException {
		ReflectionTestUtils.setField(subject, "fortunesAPI", "http://localhost:8082/fortunes/random");
		URI falseUrl = new URI(url);
		when(restTemplate.exchange(falseUrl, eq(HttpMethod.GET), any(HttpEntity.class), 
				eq(String.class))).thenThrow(ResourceAccessException.class);
		
		final String expected = "\"You've met with a terrible fate, haven't you?\"";
		
		String response = subject.findMyFate();
		
		verify(subject).getFallbackFortune(any(ResourceAccessException.class));
		assertThat(response).isEqualTo(expected);
	}
}
