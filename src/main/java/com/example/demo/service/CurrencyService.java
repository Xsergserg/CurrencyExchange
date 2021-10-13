package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.Currency;
import com.example.demo.domain.RequestData;
import com.example.repository.Repository;

public class CurrencyService implements Service{
	@Autowired
	private Repository repository;
	
	public CurrencyService() {
		super();
	}
	
	@Override
	public String getResponse(String parameters) {
		String response;
		if (parameters == null) {
			response = "Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/\"100, USD, RUR\" or localhost:8080/str=\"100, USD, RUR\"";
			return response;
		}
		RequestData requestData = new RequestData();
		requestData.setRequestCurrencyData(parameters);
		if (!requestData.isCorrect()) {
			response = "Error: Wrong string format";
			return response;
		}
		try {
			repository.requestCurrency("http://www.cbr.ru/scripts/XML_daily.asp");
		} catch (Exception e) {
			response = "Error: connection error, try later please\n";
			return response;
		}
		if (repository.getCurrencies() == null) {
			response = "Error: connection error, try later please\n";
			return response;
		}
		String sourceCurrencyCharCode = requestData.getSourceCurrencyCharCode();
		String targetCurrencyCharCode = requestData.getTargetCurrencyCharCode();
		ArrayList<Currency> currencyList = repository.getCurrencies();
		Double sourceNominal = null;
		Double targetNominal = null;
		Double sourceValue = null;
		Double targetValue = null;
		Double value = requestData.getValue();
		for (int i = 0; i < currencyList.size(); i++) {
			if (currencyList.get(i).getCharCode().equals(sourceCurrencyCharCode)) {
				sourceValue = currencyList.get(i).getValue();
				sourceNominal = currencyList.get(i).getNominal();
			}
			if (currencyList.get(i).getCharCode().equals(targetCurrencyCharCode)) {
				targetValue = currencyList.get(i).getValue();
				targetNominal = currencyList.get(i).getNominal();
			}
		}
		response = currencyCalculate(value, sourceNominal, sourceValue, targetValue, targetNominal);
		return response;
	}

	public String currencyCalculate(Double value, Double sourceNominal, Double sourceValue, Double targetValue,
			Double targetNominal) {
		if (sourceNominal == null | targetNominal == null | sourceValue == null | targetValue == null) {
			return "Error: unknown currency char code";
		}
		return String.valueOf(value / sourceNominal * sourceValue / targetValue * targetNominal);
	}
}
