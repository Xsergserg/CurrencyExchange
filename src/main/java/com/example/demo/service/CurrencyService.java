package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.controller.RequestParameters;
import com.example.demo.domain.Currency;
import com.example.repository.Repository;

public class CurrencyService {
	@Autowired
	private Repository repository;

	public String currencyExchange(String valueStr, String sourceCurrencyCharCode, String targetCurrencyCharCode) {
		Double value;
		try {
			value = Double.parseDouble(valueStr);
			if (value < 0) {
				return "Error: service works only with positive numbers";
			}
		} catch (Exception e) {
			return "Error: Wrong string format";
		}
		try {
			repository.requestCurrency("http://www.cbr.ru/scripts/XML_daily.asp");
		} catch (Exception e) {
			return "Error: connection error, try later please\n";
		}
		if (repository.getCurrencies() == null) {
			return "Error: data is not available\n";
		}
		ArrayList<Currency> currencyList = repository.getCurrencies();
		Double sourceNominal = null;
		Double targetNominal = null;
		Double sourceValue = null;
		Double targetValue = null;
		sourceCurrencyCharCode = sourceCurrencyCharCode.toUpperCase();
		targetCurrencyCharCode = targetCurrencyCharCode.toUpperCase();
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
		return currencyCalculate(value, sourceNominal, sourceValue, targetValue, targetNominal);
	}

	public String currencyCalculate(Double value, Double sourceNominal, Double sourceValue, Double targetValue,
			Double targetNominal) {
		if (sourceNominal == null | targetNominal == null | sourceValue == null | targetValue == null) {
			return "Error: unknown currency char code";
		}
		return String.valueOf(value / sourceNominal * sourceValue / targetValue * targetNominal);
	}
}
