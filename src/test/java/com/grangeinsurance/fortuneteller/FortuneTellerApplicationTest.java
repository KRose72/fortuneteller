package com.grangeinsurance.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FortuneTellerApplicationTest {

	@Autowired
	ApplicationContext context;
	
	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
	}
}
