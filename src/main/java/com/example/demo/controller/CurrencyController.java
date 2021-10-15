package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;

@RestController
public class CurrencyController {
	@Autowired
	private CurrencyService currencyService;

	@GetMapping("/")
	public String index() {
		return "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/api/100,USD,RUR or localhost:8080/api?ammount=100&fromCurrency=USD&toCurrency=EUR";
	}

	@GetMapping("/api")
	public String currencyRequestParam(@RequestParam String amount, @RequestParam String fromCurrency,
			@RequestParam String toCurrency) {
		return currencyService.currencyExchange(amount, fromCurrency, toCurrency);
	}

	@GetMapping("/api/{parameters}")
	public String currencyRequestPathVariable(@PathVariable String parameters) {
		RequestParameters requestParameters = RequestParameters.parse(parameters);
		return currencyService.currencyExchange(requestParameters.getValue(), requestParameters.getSourceCurrencyCharCode(),
				requestParameters.getTargetCurrencyCharCode());
	}
}