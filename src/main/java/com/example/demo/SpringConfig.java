package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.RequestParameters;
import com.example.demo.service.CurrencyService;
import com.example.repository.CurrencyRepository;

@Configuration
public class SpringConfig {

	@Bean
	public CurrencyService currencyService() {
		return new CurrencyService();
	}
	@Bean
	public CurrencyRepository currencyRepository() {
		return new CurrencyRepository();
	}
	@Bean
	public RequestParameters requestParameters() {
		return new RequestParameters();
	}
}
