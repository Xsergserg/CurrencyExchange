package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RequestData {
	private double value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;
	private boolean isCorrect = true;
	
	public RequestData() {
		super();
	}
	
	public RequestData(double value, String sourceCurrencyCharCode, String targetCurrencyCharCode, boolean isCorrect) {
		super();
		this.value = value;
		this.sourceCurrencyCharCode = sourceCurrencyCharCode;
		this.targetCurrencyCharCode = targetCurrencyCharCode;
		this.isCorrect = isCorrect;
	}
	
	public void setRequestCurrencyData(String parametersStr) {
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
			isCorrect = false;
			return;
		}
		try {
			value = Double.parseDouble(parameters.get(0));
			if (value < 0) {
				isCorrect = false;
				return;
			}
		} catch (Exception e) {
			isCorrect = false;
			return;
		}
		sourceCurrencyCharCode = parameters.get(1).toUpperCase();
		targetCurrencyCharCode = parameters.get(2).toUpperCase();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestData other = (RequestData) obj;
		return isCorrect == other.isCorrect && Objects.equals(sourceCurrencyCharCode, other.sourceCurrencyCharCode)
				&& Objects.equals(targetCurrencyCharCode, other.targetCurrencyCharCode)
				&& Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

	public double getValue() {
		return value;
	}

	public String getSourceCurrencyCharCode() {
		return sourceCurrencyCharCode;
	}

	public String getTargetCurrencyCharCode() {
		return targetCurrencyCharCode;
	}

	public boolean isCorrect() {
		return isCorrect;
	}
}
