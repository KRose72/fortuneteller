package com.grangeinsurance.fortuneteller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FortuneTellerController {

private FortuneTellerService service;
	
	public FortuneTellerController(FortuneTellerService service) { this.service= service;	}
	
	@GetMapping("/findmyfate")
	public String findMyFateMethod() {
		return service.findMyFate();
	}
}
