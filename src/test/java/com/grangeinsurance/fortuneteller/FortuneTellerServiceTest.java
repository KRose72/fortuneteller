package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FortuneTellerServiceTest {

	private FortuneTellerService subject;
	
	@BeforeEach
	void setup() {
		subject = new FortuneTellerService();
	}
	
	@Test
	void serviceReturnsRandomFortune() {
		final String expected = "Test";
		
		String response = subject.findMyFate();
	    assertThat(response.getClass().getSimpleName()).isEqualTo(expected.getClass().getSimpleName());
	}
	
}
