package com.example.demo.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;
import com.example.demo.service.CurrencyService;




@RestController
public class CurrencyController {
	private CurrencyService currencyService;
	
	@Autowired
	public CurrencyController(CurrencyService currencyService) {
			this.currencyService = currencyService;
	}
	
	@GetMapping("/")
	public String index() {
		return "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/currencyexchange/100,USD,RUR or localhost:8080/currencyexchange?amount=100&fromCurrency=USD&toCurrency=EUR";
	}

	@GetMapping("/currencyexchange")
	public String currencyRequestParam(@RequestParam Double amount, @RequestParam String fromCurrency,
			@RequestParam String toCurrency) {
		RequestParameters requestParameters = new RequestParameters(amount, fromCurrency, toCurrency);
		return currencyService.currencyExchange(requestParameters);
	}

	@GetMapping("/currencyexchange/{parameters}")
	public String currencyRequestPathVariable(@PathVariable String parameters) {
		RequestParameters requestParameters = RequestParameters.parse(parameters);
		return currencyService.currencyExchange(requestParameters);
	}
}
