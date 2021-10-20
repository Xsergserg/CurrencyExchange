package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Currency;
import com.example.demo.dto.CurrencyList;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class CurrencyService {
	@Autowired
	private RequestCurrencyFromCBRService requestCurrencyFromCBRService;

	public String currencyExchange(RequestParameters requestParameters) throws Exception {
		ArrayList<Currency> currenciesFromCBR = requestCurrencyFromCBRService
				.requestCurrencies("http://www.cbr.ru/scripts/XML_daily.asp");
		CurrencyList currencyList = new CurrencyList(currenciesFromCBR);
		Currency sourceCurrency = currencyList.getCurrencyByCharCode(requestParameters.getSourceCurrencyCharCode());
		Currency targetCurrency = currencyList.getCurrencyByCharCode(requestParameters.getTargetCurrencyCharCode());
		return currencyCalculate(requestParameters.getValue(), sourceCurrency.getNominal(), sourceCurrency.getValue(),
				targetCurrency.getNominal(), targetCurrency.getValue());
	}

	public String currencyCalculate(Double value, Double sourceNominal, Double sourceValue, Double targetNominal,
			Double targetValue) throws Exception {
		if (sourceNominal == null | targetNominal == null | sourceValue == null | targetValue == null) {
			throw new CurrencyExchangeException("500");
		}
		return String.valueOf(value / sourceNominal * sourceValue / targetValue * targetNominal);
	}
}
