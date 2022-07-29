package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FortuneTellerControllerTest {

@Mock FortuneTellerService mockService;
	
	private FortuneTellerController subject;
	
	@BeforeEach
	void setup() {
		subject = new FortuneTellerController(mockService);
	}
	
	@Test
	void fortunesControllerInitialized() {
		assertThat(subject).isNotNull();
	}
	
	@Test
	void controllerReturnsRandomFortune() {
		final String expected = "Test";
		
		when(mockService.findMyFate()).thenReturn(expected);
		String response = subject.findMyFateMethod();
	    assertThat(response.getClass().getSimpleName()).isEqualTo(expected.getClass().getSimpleName());
	}
}
