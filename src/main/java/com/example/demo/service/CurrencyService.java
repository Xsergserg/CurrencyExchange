package com.example.demo.service;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.example.demo.domain.Currency;
import com.example.demo.domain.CurrencyLog;
import com.example.demo.dto.ExchangeRates;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;
import com.example.demo.repository.CurrencyRateRequestsLoggerRepository;
import com.example.demo.repository.ExchangeRateListRepository;

@Service
public class CurrencyService {
	private ExchangeRateListRepository exchangeRateListRepository;
	private CurrencyRateRequestsLoggerRepository сurrencyRateRequestsLoggerRepository;

	@Autowired
	public CurrencyService(
			@Qualifier("cachedExchangeRateListRepository") ExchangeRateListRepository exchangeRateListRepository,
			CurrencyRateRequestsLoggerRepository currencyRateRequestsLoggerRepository) {
		this.exchangeRateListRepository = exchangeRateListRepository;
		this.сurrencyRateRequestsLoggerRepository = currencyRateRequestsLoggerRepository;
	}

	public String currencyExchange(RequestParameters requestParameters) {
		try {
			ExchangeRates exchangedRates = exchangeRateListRepository.getExchangeRates();
			Currency sourceCurrency = exchangedRates.getCurrencyByCode(requestParameters.getSourceCurrencyCharCode());
			Currency targetCurrency = exchangedRates.getCurrencyByCode(requestParameters.getTargetCurrencyCharCode());
			Double value = requestParameters.getValue();
			Double sourceNominal = sourceCurrency.getNominal();
			Double targetNominal = sourceCurrency.getValue();
			Double sourceValue = targetCurrency.getNominal();
			Double targetValue = targetCurrency.getValue();
			Double result = value / sourceNominal * sourceValue / targetValue * targetNominal;
			сurrencyRateRequestsLoggerRepository.save(new CurrencyLog(requestParameters, result));
			return String.valueOf(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
