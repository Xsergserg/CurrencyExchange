package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.example.demo.exception.CurrencyExchangeException;


public class RequestParameters {
	private Double value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;

	
	public RequestParameters(Double value, String sourceCurrencyCharCode, String targetCurrencyCharCode) throws Exception {
		super();
		if (value < 0) {
			throw new CurrencyExchangeException("400");
		}
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
			throw new CurrencyExchangeException("400");
		}
		Double value = Double.parseDouble(parameters.get(0));
		return new RequestParameters(value, parameters.get(1), parameters.get(2));
	}

	/*
	 * public static Double parseValue(String valueStr) throws Exception { Double
	 * value; try { value = Double.parseDouble(valueStr); } catch (Exception e) {
	 * throw new CurrencyExchangeException("400"); } if (value < 0) { throw new
	 * CurrencyExchangeException("400"); } return value; }
	 */
	
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
