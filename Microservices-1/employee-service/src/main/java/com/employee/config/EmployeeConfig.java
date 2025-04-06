package com.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmployeeConfig {
	
	@Value("${address.base.url}")
	private String addressBaseUrl;
	
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}
	
	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().baseUrl(addressBaseUrl).build();
	}
	

}
