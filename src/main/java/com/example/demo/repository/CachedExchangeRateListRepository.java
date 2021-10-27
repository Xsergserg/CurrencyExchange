package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.domain.Currency;
import com.example.demo.dto.ExchangeRates;

@Repository
public class CachedExchangeRateListRepository implements ExchangeRateListRepository {

	private ExchangeRates exchangeRates;
	private ExchangeRateListRepository baseRepo;
	
	@Autowired
	public CachedExchangeRateListRepository(@Qualifier("httpExchangeRateListRepository") ExchangeRateListRepository baseRepo) {
		this.exchangeRates = null;
		this.baseRepo = baseRepo;
	}

	@Override
	public ExchangeRates getExchangeRates() {
		if (exchangeRates != null) {
			return exchangeRates;
		}
		return baseRepo.getExchangeRates();
	}
}
