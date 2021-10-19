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
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class RequestCBRService {
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

	public Currency getCurrency(String charCode, ArrayList<Currency> currencyList) throws Exception {
		for (int i = 0; i < currencyList.size(); i++) {
			if (currencyList.get(i).getCharCode().equals(charCode)) {
				return currencyList.get(i);
			}
		}
		throw new CurrencyExchangeException("500");
	}
	
}
