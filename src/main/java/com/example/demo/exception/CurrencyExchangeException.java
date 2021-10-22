package com.example.demo.exception;


//рантайм ексцепшн чек и анчек
// рантайм ексепшн вместо экзепшен
// нельзя использовать ексцепшн 
// Аннотации для исключением кастомреезкод фор кастом экзепшн
public class CurrencyExchangeException extends Exception {
	public CurrencyExchangeException (String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return "Error: " + super.getMessage();
	}
	
}
