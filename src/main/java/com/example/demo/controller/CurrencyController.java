package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CurrencyService;
import com.example.demo.service.Service;
import com.example.repository.CurrencyRepository;

@RestController
public class CurrencyController {
	@Autowired
    private Service service;
	
	@GetMapping("/")
	public String index() {
		return "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/\"100, USD, RUR\" or localhost:8080/str=\"100, USD, RUR\"";
	}
	
	@GetMapping("/{parameters}")
	public String currencyRequest(@PathVariable String parameters) {
		
		//Service service = new CurrencyService(new CurrencyRepository()); 
		return service.getResponse(parameters);
	}
}