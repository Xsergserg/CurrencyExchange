package com.example.demo.dto;

import java.util.List;

import com.example.demo.domain.Currency;
import com.example.demo.exception.CurrencyExchangeException;

public class ExchangeRates {
	private List<Currency> exchangedRates;

	public ExchangeRates(List<Currency> exchangedRates) {
		this.exchangedRates = exchangedRates;
	}
	
	public Currency getCurrencyByCode(String charCode) {
		Currency currency = exchangedRates.stream().filter(x -> x.getCharCode().equals(charCode)).findAny().orElse(null);
		if (currency == null) {
			throw new CurrencyExchangeException("Currency '" + charCode + "' not found");
		}
		return currency;
	}
}
