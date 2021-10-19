package com.example.demo.service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.domain.Currency;
import com.example.demo.dto.RequestParameters;
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class CurrencyService {

	public String currencyExchange(RequestParameters requestParameters) throws Exception {
		ArrayList<Currency> currencyList;
			currencyList = requestCurrency("http://www.cbr.ru/scripts/XML_daily.asp");
			Double sourceNominal = null;
			Double targetNominal = null;
			Double sourceValue = null;
			Double targetValue = null;
			String sourceCurrencyCharCode = requestParameters.getSourceCurrencyCharCode();
			String targetCurrencyCharCode = requestParameters.getTargetCurrencyCharCode();
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
		return currencyCalculate(requestParameters.getValue(), sourceNominal, sourceValue, targetValue, targetNominal);
	}

	public String currencyCalculate(Double value, Double sourceNominal, Double sourceValue, Double targetValue,
			Double targetNominal) throws Exception {
		if (sourceNominal == null | targetNominal == null | sourceValue == null | targetValue == null) {
			throw new CurrencyExchangeException("unknown currency char code");
		}
		return String.valueOf(value / sourceNominal * sourceValue / targetValue * targetNominal);
	}

	public ArrayList<Currency> requestCurrency(String urlStr) throws Exception {
		try {
			ArrayList<Currency> currencies = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			URL url = new URL(urlStr);
			InputStream stream = url.openStream();
			Document doc = docBuilder.parse(stream);
			Node root = doc.getDocumentElement();
			NodeList currencyNodes = root.getChildNodes();

			for (int i = 0; i < currencyNodes.getLength(); i++) {
				Node currency = currencyNodes.item(i);
				if (currency.getNodeType() != Node.TEXT_NODE) {
					NodeList currencyProps = currency.getChildNodes();
					String CharCode = null;
					double nominal = 0;
					double value = 0;
					for (int j = 0; j < currencyProps.getLength(); j++) {

						Node currencyProp = currencyProps.item(j);
						if (currencyProp.getNodeType() != Node.TEXT_NODE) {
							if (currencyProp.getNodeName().equals("CharCode")) {
								CharCode = currencyProp.getChildNodes().item(0).getTextContent();
							}
							if (currencyProp.getNodeName().equals("Nominal")) {
								nominal = Double.parseDouble(
										currencyProp.getChildNodes().item(0).getTextContent().replace(',', '.'));
							}
							if (currencyProp.getNodeName().equals("Value")) {
								value = Double.parseDouble(
										currencyProp.getChildNodes().item(0).getTextContent().replace(',', '.'));
							}
						}
					}
					if (CharCode != null) {
						if (currencies == null) {
							currencies = new ArrayList<Currency>();
							currencies.add(new Currency("RUR", 1.0, 1.0));
						}
						currencies.add(new Currency(CharCode, nominal, value));
					}
				}
			}
			if (currencies == null) {
				throw new CurrencyExchangeException("500");
			}
			return currencies;
		} catch (Exception e) {
			throw new CurrencyExchangeException("500");
		}
	}
}
