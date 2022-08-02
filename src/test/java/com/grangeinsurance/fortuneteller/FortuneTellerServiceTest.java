package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class FortuneTellerServiceTest {

	private FortuneTellerService subject;
	
	@Value("${fortunesAPI}")
	String url;
	
	@BeforeEach
	void setup() {
		subject = new FortuneTellerService();
	}
	
	@Test
	void serviceReturnsRandomFortune() {
		final String expected = "Test";
		ReflectionTestUtils.setField(subject, "fortunesAPI", "http://localhost:8080/fortunes/random");
		
		String response = subject.findMyFate();
		assertThat(url).isEqualTo(subject.fortunesAPI);
	    assertThat(response.getClass().getSimpleName()).isEqualTo(expected.getClass().getSimpleName());
	}
	
}
