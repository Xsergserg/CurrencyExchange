package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RequestParameters {
	private String value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;
	
	public RequestParameters(String value, String sourceCurrencyCharCode, String targetCurrencyCharCode) {
		super();
		this.value = value;
		this.sourceCurrencyCharCode = sourceCurrencyCharCode;
		this.targetCurrencyCharCode = targetCurrencyCharCode;
	}

	public static RequestParameters parse(String parametersStr) {
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
			return new RequestParameters(null, null, null);
		}
		return new RequestParameters(parameters.get(0), parameters.get(1), parameters.get(2));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(sourceCurrencyCharCode, targetCurrencyCharCode, value);
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

	public String getValue() {
		return value;
	}

	public String getSourceCurrencyCharCode() {
		return sourceCurrencyCharCode;
	}

	public String getTargetCurrencyCharCode() {
		return targetCurrencyCharCode;
	};	
}
