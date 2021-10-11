package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CurrencyData {
	public CurrencyData(double value, String sourceCurrencyCharCode, String targetCurrencyCharCode, boolean isCorrect) {
		super();
		this.value = value;
		this.sourceCurrencyCharCode = sourceCurrencyCharCode;
		this.targetCurrencyCharCode = targetCurrencyCharCode;
		this.isCorrect = isCorrect;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyData other = (CurrencyData) obj;
		return isCorrect == other.isCorrect && Objects.equals(sourceCurrencyCharCode, other.sourceCurrencyCharCode)
				&& Objects.equals(targetCurrencyCharCode, other.targetCurrencyCharCode)
				&& Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

	private double value;
	private String sourceCurrencyCharCode;
	private String targetCurrencyCharCode;
	private boolean isCorrect = true;

	public CurrencyData(String str) {
		super();
		str = str.trim();
		if (str.length() > 0) {
			if ((str.charAt(0) == '"' & str.charAt(str.length() - 1) == '"')
					|| (str.charAt(0) == '\'' & str.charAt(str.length() - 1) == '\'')) {
				str = str.substring(1, str.length() - 1);
			}
		}
		ArrayList<String> properties = new ArrayList<String>(Arrays.asList(str.split("[ ,]")));
		properties.removeAll(Arrays.asList("", null));
		if (properties.size() != 3) {
			isCorrect = false;
			return;
		}
		try {
			value = Double.parseDouble(properties.get(0));
			if (value < 0) {
				isCorrect = false;
				return;
			}
		} catch (Exception e) {
			isCorrect = false;
			return;
		}
		sourceCurrencyCharCode = properties.get(1).toUpperCase();
		targetCurrencyCharCode = properties.get(2).toUpperCase();
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

	boolean isCorrect() {
		return isCorrect;
	}
}
