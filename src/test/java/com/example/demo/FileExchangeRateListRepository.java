package com.example.demo;

import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.demo.domain.Currency;
import com.example.demo.dto.ExchangeRates;
import com.example.demo.repository.ExchangeRateListRepository;

import org.w3c.dom.Document;

public class FileExchangeRateListRepository implements ExchangeRateListRepository {
	
	public ExchangeRates exchangeRates;
	
	public FileExchangeRateListRepository() {
		List <Currency> currencies= new LinkedList();
		currencies.add(new Currency("RUR", 1.0, 1.0));
		currencies.add(new Currency("USD", 1.0, 75.0));
		currencies.add(new Currency("EUR", 1.0, 85.0));
		ExchangeRates exchangeRates = new ExchangeRates(currencies); 
		this.exchangeRates = exchangeRates;
	}


	@Override
	public ExchangeRates getExchangeRates() {		
		return exchangeRates;
	}
}
