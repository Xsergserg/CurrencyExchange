package com.example.demo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import com.example.demo.exception.CurrencyExchangeException;
import com.example.demo.service.CurrencyFromCBRService;

public class CurrencyFromFile extends CurrencyFromCBRService {
	@Override
	public Document requestCurrencyDocument() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(
					"D:\\workspace-spring-tool-suite\\CurrencyExchange\\src\\test\\java\\com\\example\\demo\\resources\\XML_daily.asp");
			return xmlDocument;
		} catch (Exception e) {
			throw new CurrencyExchangeException("Currency with requested char code not found");
		}
	}
}
