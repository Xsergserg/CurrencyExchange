package com.example.demo.service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.domain.Currency;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class CurrencyService {
	@Autowired
	private RequestCBRService requestCBRService;

	public String currencyExchange(RequestParameters requestParameters) throws Exception {
		ArrayList<Currency> currencyList;
		currencyList = requestCBRService.requestCurrency("http://www.cbr.ru/scripts/XML_daily.asp");
		Currency sourceCurrency = requestCBRService.getCurrency(requestParameters.getSourceCurrencyCharCode(),
				currencyList);
		Currency targetCurrency = requestCBRService.getCurrency(requestParameters.getTargetCurrencyCharCode(),
				currencyList);
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
