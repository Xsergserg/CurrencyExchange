package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.util.Assert;


@SpringBootTest
public class CurrencyRepositoryTest {

	@Test
	public void wrongUrlTest() throws Exception {
		CurrencyRepository currencyRepository = new CurrencyRepository("http://www.cbr.ru/");
		Assert.isTrue(currencyRepository.getCurrencies() == null, "Wrong url");
	}
	@Test
	public void unexistUrlTest() throws Exception {
		CurrencyRepository currencyRepository = new CurrencyRepository("http://www.cbr.ru/");
		Assert.isTrue(currencyRepository.getCurrencies() == null, "Unexist url");
	}
	@Test
	public void rigthUrlTest() throws Exception {
		CurrencyRepository currencyRepository = new CurrencyRepository("http://www.cbr.ru/scripts/XML_daily.asp");
		Assert.notNull(currencyRepository.getCurrencies(), "Right url");
	}
}