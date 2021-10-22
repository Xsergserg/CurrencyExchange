package com.example.demo.dto;

import java.util.ArrayList;

import com.example.demo.domain.Currency;
import com.example.demo.exception.CurrencyExchangeException;

public class CurrencyList {
	//интерфейсы справа
	public ArrayList<Currency> currencies;
	
	public CurrencyList(ArrayList<Currency> currencies) {
		//супер только для расшираемых в дальнейшем классе
		super();
		this.currencies = currencies;
	}

	public Currency getCurrencyByCharCode(String charCode) throws Exception {
		for (int i = 0; i < currencies.size(); i++) {
			if (currencies.get(i).getCharCode().equals(charCode)) {
				return currencies.get(i);
			}
		}
		throw new CurrencyExchangeException("500");
	}
}
