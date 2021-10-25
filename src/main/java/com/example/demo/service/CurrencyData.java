package com.example.demo.service;

import org.w3c.dom.Document;

import com.example.demo.domain.Currency;
import com.example.demo.exception.CurrencyExchangeException;

public interface CurrencyData {
	public Document requestCurrencyDocument();
}
