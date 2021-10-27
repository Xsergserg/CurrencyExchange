package com.example.demo.repository;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.domain.Currency;
import com.example.demo.dto.ExchangeRates;
import com.example.demo.exception.CurrencyExchangeException;

@Repository
public class HttpExchangeRateListRepository implements ExchangeRateListRepository {

	@Override
	public ExchangeRates getExchangeRates() {
		List<Currency> exchangeRateList = new LinkedList();
		exchangeRateList.add(new Currency("RUR", 1.0, 1.0));
		Document xmlDocument;
		try (InputStream inputStream = (new URL("http://www.cbr.ru/scripts/XML_daily.asp")).openStream()) {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(inputStream);
		} catch (Exception e) {
			throw new CurrencyExchangeException("Connection error");
		}
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList currencies;
		try {
			currencies = (NodeList) xPath.compile("/ValCurs/Valute").evaluate(xmlDocument, XPathConstants.NODESET);
		} catch (Exception e) {
			throw new CurrencyExchangeException("XML file critical parse error");
		}
		for (int i = 0; i < currencies.getLength(); i++) {
			Currency currency = currencyParser(currencies.item(i), xPath);
			if (currency != null) {
				exchangeRateList.add(currency);
			}	
		}
		return new ExchangeRates(exchangeRateList);
	}
	
	private Currency currencyParser(Node node, XPath xPath) {
		Currency currency = null;
		try {
			String charCode = xPath.compile("./CharCode").evaluate(node);
			String nominalStr = xPath.compile("./Nominal").evaluate(node);
			String valueStr = xPath.compile("./Value").evaluate(node);
			Double nominal = Double.parseDouble(nominalStr.replace(',', '.'));
			Double value = Double.parseDouble(valueStr.replace(',', '.'));
			currency = new Currency(charCode, nominal, value);
		} catch (Exception e) {
		}
			return currency;	
	}
}