package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.example.demo.exception.CurrencyExchangeException;


public class RequestParameters {
	private Double value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;

	
	public RequestParameters(Double value, String sourceCurrencyCharCode, String targetCurrencyCharCode) {
		super();
		this.value = value;
		this.sourceCurrencyCharCode = sourceCurrencyCharCode;
		this.targetCurrencyCharCode = targetCurrencyCharCode;
	}

	public static RequestParameters parse(String parametersStr) throws Exception {
		parametersStr = parametersStr.trim();
		if (parametersStr.length() > 0) {
			if ((parametersStr.charAt(0) == '"' & parametersStr.charAt(parametersStr.length() - 1) == '"')
					|| (parametersStr.charAt(0) == '\'' & parametersStr.charAt(parametersStr.length() - 1) == '\'')) {
				parametersStr = parametersStr.substring(1, parametersStr.length() - 1);
			}
		}
		ArrayList<String> parameters = new ArrayList<String>(Arrays.asList(parametersStr.split("[ ,]")));
		parameters.removeAll(Arrays.asList("", null));
		if (parameters.size() != 3) {
			throw new CurrencyExchangeException("Not ehough arguments");
		}
		Double value = parseValue(parameters.get(0));
		return new RequestParameters(value, parameters.get(1), parameters.get(2));
	}

	public static Double parseValue(String valueStr) throws Exception {
		Double value;
		try {
			value = Double.parseDouble(valueStr);
		} catch (Exception e) {
			throw new CurrencyExchangeException("value format is not double\n");
		}
		if (value < 0) {
			throw new CurrencyExchangeException("service works only with positive numbers");
		}
		return value;
	}
	
	public Double getValue() {
		return value;
	}

	public String getSourceCurrencyCharCode() {
		return sourceCurrencyCharCode;
	}

	public String getTargetCurrencyCharCode() {
		return targetCurrencyCharCode;
	};	
}
