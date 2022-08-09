package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
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
	void serviceReturnsDefaultFortuneWhenException() {
		ReflectionTestUtils.setField(subject, "fortunesAPI", "http://localhost:8080/fortunes/random");
		
		final String expected = "\"You've met with a terrible fate, haven't you?\"";
		
		String response = subject.getFallbackFortune(null);
		
		assertThat(url).isEqualTo(subject.fortunesAPI);
	    assertThat(response).isEqualTo(expected);
	}
	
}
