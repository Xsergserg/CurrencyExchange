package com.example.demo.repository;

import java.util.List;

import org.w3c.dom.Document;

import com.example.demo.domain.Currency;
import com.example.demo.dto.ExchangeRates;

public interface ExchangeRateListRepository {
	ExchangeRates getExchangeRates();
}
