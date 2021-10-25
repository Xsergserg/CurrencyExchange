package com.example.demo.service;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.example.demo.domain.Currency;
import com.example.demo.domain.CurrencyLogData;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;
import com.example.demo.repository.CurrencyExchangeRepository;

@Service
public class CurrencyService {
	private CurrencyData currencyData;
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	public CurrencyService(CurrencyData currencyData, CurrencyExchangeRepository currencyExchangeRepository) {
		this.currencyData = currencyData;
		this.currencyExchangeRepository = currencyExchangeRepository;
	}

	public String currencyExchange(RequestParameters requestParameters) {
		Double result = Double.NaN;
		try {
			Document currencyFromDocument = currencyData.requestCurrencyDocument();
			Currency sourceCurrency = requestCurrency(requestParameters.getSourceCurrencyCharCode(),
					currencyFromDocument);
			Currency targetCurrency = requestCurrency(requestParameters.getTargetCurrencyCharCode(),
					currencyFromDocument);
			Double value = requestParameters.getValue();
			Double sourceNominal = sourceCurrency.getNominal();
			Double targetNominal = sourceCurrency.getValue();
			Double sourceValue = targetCurrency.getNominal();
			Double targetValue = targetCurrency.getValue();
			if (sourceNominal.isNaN() || targetNominal.isNaN() || sourceValue.isNaN() || targetValue.isNaN()) {
				throw new CurrencyExchangeException("Some of requested data not found");
			}
			result = value / sourceNominal * sourceValue / targetValue * targetNominal;
		} catch (Exception e) {
			throw e;
		} finally {
			currencyExchangeRepository.save(new CurrencyLogData(requestParameters, result));
		}
		return String.valueOf(result);
	}

	public Currency requestCurrency(String charCode, Document xmlDocument) {
		try {
			if (charCode.equals("RUR")) {
				return new Currency("RUR", 1.0, 1.0);
			}
			XPath xPath = XPathFactory.newInstance().newXPath();
			String nominalExpression = "/ValCurs/Valute[CharCode='" + charCode + "']/Nominal/text()";
			String valueExpression = "/ValCurs/Valute[CharCode='" + charCode + "']/Value/text()";
			String nominalStr = (String) xPath.compile(nominalExpression).evaluate(xmlDocument, XPathConstants.STRING);
			String valueStr = (String) xPath.compile(valueExpression).evaluate(xmlDocument, XPathConstants.STRING);
			Double nominal = Double.parseDouble(nominalStr.replace(',', '.'));
			Double value = Double.parseDouble(valueStr.replace(',', '.'));
			return new Currency(charCode, nominal, value);
		} catch (Exception e) {
			throw new CurrencyExchangeException("Currency with requested char code not found");
		}
	}
}
