package com.example.demo.exception;

public class CurrencyExchangeException extends Exception {
	public CurrencyExchangeException (String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return "Error: " + super.getMessage();
	}
	
}
