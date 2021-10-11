package com.example.demo;

public class CurrencyClass {
	private String CharCode;
	private Double nominal;
	private Double value;
	public CurrencyClass(String name, Double nominal, Double value) {
		super();
		this.CharCode = name;
		this.nominal = nominal;
		this.value = value;
	}
	public String getCharCode() {
		return CharCode;
	}
	public Double getNominal() {
		return nominal;
	}
	public Double getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "Currency [CharCode=" + CharCode + ", nominal=" + nominal + ", value=" + value + "]";
	}
}
