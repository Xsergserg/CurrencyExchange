package com.example.demo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import com.example.demo.service.CurrencyData;

public class CurrencyFromFileService implements CurrencyData {
	@Override
	public Document requestCurrencyDocument() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(
					"src/test/java/com/example/demo/resources/XML_daily.asp");
			return xmlDocument;
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		}
		return null;
	}
}
