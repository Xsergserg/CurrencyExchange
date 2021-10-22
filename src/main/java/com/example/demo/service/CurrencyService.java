package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.example.demo.domain.Currency;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class CurrencyService {
	private CurrencyData currencyData;

	@Autowired
	public CurrencyService(CurrencyData currencyData) {
		this.currencyData = currencyData;
	}

	public String currencyExchange(RequestParameters requestParameters) {
		Document currencyFromDocument = currencyData.requestCurrencyDocument();
		Currency sourceCurrency = currencyData.requestCurrency(requestParameters.getSourceCurrencyCharCode(), currencyFromDocument);
		Currency targetCurrency = currencyData.requestCurrency(requestParameters.getTargetCurrencyCharCode(), currencyFromDocument);
		Double value = requestParameters.getValue();
		Double sourceNominal = sourceCurrency.getNominal();
		Double targetNominal = sourceCurrency.getValue();
		Double sourceValue = targetCurrency.getNominal();
		Double targetValue = targetCurrency.getValue();
		if (sourceNominal.isNaN() || targetNominal.isNaN() || sourceValue.isNaN() || targetValue.isNaN()) {
			throw new CurrencyExchangeException("Some of requested data not found");
		}
		return String.valueOf( value / sourceNominal * sourceValue / targetValue * targetNominal);
	}
}
