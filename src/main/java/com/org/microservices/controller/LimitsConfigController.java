package com.org.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.org.microservices.bean.LimitsConfiguration;
import com.org.microservices.configuration.Configuration;

@RestController
public class LimitsConfigController {
	
	@Autowired
	private Configuration configuration; 

	@GetMapping(path = "/limits")
	public LimitsConfiguration retreiveLimitsFromConfiguration() {
		return new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}
	
	@GetMapping(path = "faulte-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetreiveConfiguration")
	public LimitsConfiguration retreiveConfiguation() {
		throw new RuntimeException("Not Available");
	}
	
	public LimitsConfiguration fallbackRetreiveConfiguration() {
		
		return new LimitsConfiguration(99, 9999);
	}
}