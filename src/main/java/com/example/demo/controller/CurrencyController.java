package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RequestParameters;
import com.example.demo.service.CurrencyService;




@RestController
public class CurrencyController {
	//здесь должен быть конструктор
	@Autowired
	private CurrencyService currencyService;

	@GetMapping("/")
	public String index() {
		return "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/api/100,USD,RUR or localhost:8080/api?amount=100&fromCurrency=USD&toCurrency=EUR";
	}
	//переименовать 
	@GetMapping("/api")
	public String currencyRequestParam(@RequestParam Double amount, @RequestParam String fromCurrency,
			@RequestParam String toCurrency) throws Exception {
		RequestParameters requestParameters = new RequestParameters(amount, fromCurrency, toCurrency);
		return currencyService.currencyExchange(requestParameters);
	}

	@GetMapping("/api/{parameters}")
	public String currencyRequestPathVariable(@PathVariable String parameters) throws Exception {
		RequestParameters requestParameters = RequestParameters.parse(parameters);
		return currencyService.currencyExchange(requestParameters);
	}
}
