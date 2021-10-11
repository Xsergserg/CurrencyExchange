package com.example.demo;

import java.util.ArrayList;
//import java.util.stream.Stream;

public class CurrencyService {
	public String result;

	public CurrencyService(String str) {
		super();
		CurrencyData currencyData = new CurrencyData(str);
		if (!currencyData.isCorrect()) {
			result = "Error: Wrong string format";
			return;
		}
		CurrencyRepository currencyRepository;
		try {
			currencyRepository = new CurrencyRepository("http://www.cbr.ru/scripts/XML_daily.asp");
		} catch (Exception e) {
			result = "Error: connection error, try later please\n";
			return;
		}
		if (currencyRepository.getCurrencies() == null) {
			result = "Error: connection error, try later please\n";
			return;
		}
		String sourceCurrencyCharCode = currencyData.getSourceCurrencyCharCode();
		String targetCurrencyCharCode = currencyData.getTargetCurrencyCharCode();
		ArrayList<CurrencyClass> currencyList = currencyRepository.getCurrencies();
//		Stream<String> currencyCharCodesStream = currencyList.stream().map(x -> x.getCharCode());
//
//		if (!(currencyCharCodesStream.anyMatch(x -> x.equals(sourceCurrencyCharCode))
//				&& currencyCharCodesStream.anyMatch(x -> x.equals(targetCurrencyCharCode)))) {
//			result = "Error: unknown currency char code";
//			return;
//		}
		Double sourceNominal = null;
		Double targetNominal = null;
		Double sourceValue = null;
		Double targetValue = null;
		Double value = currencyData.getValue();
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
		result = currencyCalculate(value, sourceNominal, sourceValue, targetValue, targetNominal);
	}
	
	String currencyCalculate(Double value, Double sourceNominal, Double sourceValue, Double targetValue, Double targetNominal) {
		if (sourceNominal == null | targetNominal == null | sourceValue == null | targetValue == null) {
			return "Error: unknown currency char code";
		}
		return String.valueOf(value / sourceNominal * sourceValue / targetValue * targetNominal);
	}
	
	public String getResult() {
		return result;
	}

}
