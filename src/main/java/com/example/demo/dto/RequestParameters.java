package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.demo.exception.CurrencyExchangeException;

public class RequestParameters {

	private Double value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;

	public RequestParameters(Double value, String sourceCurrencyCharCode, String targetCurrencyCharCode)
			throws CurrencyExchangeException {
		super();
		if (value < 0) {
			throw new CurrencyExchangeException("Value can't be negative number");
		}
		this.value = value;
		this.sourceCurrencyCharCode = sourceCurrencyCharCode.toUpperCase();
		this.targetCurrencyCharCode = targetCurrencyCharCode.toUpperCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestParameters other = (RequestParameters) obj;
		return Objects.equals(sourceCurrencyCharCode, other.sourceCurrencyCharCode)
				&& Objects.equals(targetCurrencyCharCode, other.targetCurrencyCharCode)
				&& Objects.equals(value, other.value);
	}

	// parametersStr looks like "100,USD,EUR"
	public static RequestParameters parse(String parametersStr) {
		parametersStr = parametersStr.trim();
		parametersStr = TrimQuotesIfExist(parametersStr);
		List<String> parameters = new ArrayList<>(Arrays.asList(parametersStr.split("[ ,]")));
		parameters.removeAll(Arrays.asList("", null));
		if (parameters.size() != 3) {
			throw new CurrencyExchangeException("Parameters should include number, String, String");
		}
		Double value;
		try {
			value = Double.parseDouble(parameters.get(0));
		} catch (Exception e) {
			throw new CurrencyExchangeException("Number format error");
		}
		return new RequestParameters(value, parameters.get(1), parameters.get(2));
	}

	private static String TrimQuotesIfExist(String parametersStr) {
		if (parametersStr.length() > 0) {
			if ((parametersStr.charAt(0) == '"' & parametersStr.charAt(parametersStr.length() - 1) == '"')
					|| (parametersStr.charAt(0) == '\'' & parametersStr.charAt(parametersStr.length() - 1) == '\'')) {
				parametersStr = parametersStr.substring(1, parametersStr.length() - 1);
			}
		}
		return parametersStr;
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
