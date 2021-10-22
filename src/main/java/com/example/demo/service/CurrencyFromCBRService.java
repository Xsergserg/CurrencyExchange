package com.example.demo.service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.example.demo.domain.Currency;
import com.example.demo.exception.CurrencyExchangeException;

@Service
public class CurrencyFromCBRService implements CurrencyData {
	
	@Override
	public Document requestCurrencyDocument() {
		try {
			URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
			InputStream inputStream = url.openStream();
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(inputStream);
			return xmlDocument;
		} catch (Exception e) {
			throw new CurrencyExchangeException("Connection error");
		}
	}
	
	@Override
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
