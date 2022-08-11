package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class FortuneTellerApplicationTest {

	@Autowired
	ApplicationContext context;
	
	@Test
	void springApplicationMethodIsInvolkedOneceByMain() {
		try (MockedStatic<SpringApplication> mockSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
			mockSpringApplication.when(() -> SpringApplication.run(FortunetellerApplication.class, new String[] {})).thenReturn(null);
			FortunetellerApplication.main(new String[] {});
			mockSpringApplication.verify(() -> SpringApplication.run(FortunetellerApplication.class, new String[] {}), times(1));
		}
		
	}
	
	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
	}
}
