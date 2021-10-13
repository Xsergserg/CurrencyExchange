package com.example.demo.domain;

public class Currency {
	private String сharCode;
	private Double nominal;
	private Double value;
	public Currency(String name, Double nominal, Double value) {
		super();
		this.сharCode = name;
		this.nominal = nominal;
		this.value = value;
	}
	public String getCharCode() {
		return сharCode;
	}
	public Double getNominal() {
		return nominal;
	}
	public Double getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "Currency [CharCode=" + сharCode + ", nominal=" + nominal + ", value=" + value + "]";
	}
}
