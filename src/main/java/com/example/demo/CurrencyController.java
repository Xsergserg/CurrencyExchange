package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {
	@GetMapping("/")
	public String index(@RequestParam(required = false) String str) {
		if (str == null) {
			return "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/\"100, USD, RUR\" or localhost:8080/str=\"100, USD, RUR\"";
		}
		return this.str(str);
	}

	@GetMapping("/{str}")
	public String str(@PathVariable String str) {
		CurrencyService currencyService = new CurrencyService(str); 
		return currencyService.getResult();
	}
}
