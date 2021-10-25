package com.example.demo.service;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
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
}
