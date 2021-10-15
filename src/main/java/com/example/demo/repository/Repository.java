package com.example.demo.repository;

import java.util.ArrayList;

import com.example.demo.domain.Currency;

public interface Repository {
		public void requestCurrency(String urlStr);
		public ArrayList<Currency> getCurrencies();
}
