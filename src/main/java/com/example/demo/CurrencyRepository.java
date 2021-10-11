package com.example.demo;

import java.util.ArrayList;
import java.net.URL;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CurrencyRepository {
	private ArrayList<CurrencyClass> currencies = null;
	private Document doc;

	public ArrayList<CurrencyClass> getCurrencies() {
		return currencies;
	}

	public CurrencyRepository(String urlstr) {
		super();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			URL url = new URL(urlstr);
			InputStream stream = url.openStream();
			doc = docBuilder.parse(stream);
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
							currencies = new ArrayList<CurrencyClass>();
							currencies.add(new CurrencyClass("RUR", 1.0, 1.0));
						}
						currencies.add(new CurrencyClass(CharCode, nominal, value));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
