package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.util.Assert;

import com.example.demo.exception.Exception;
import com.example.demo.service.CurrencyService;


@SpringBootTest
public class currencyCalculateTest {
	private CurrencyService currencyService;
	
	/*
	 * @Test public void calculateTest() throws Exception {
	 * Assert.isTrue(currencyService.currencyCalculate(1.0, 2.0, 1.0, 2.0,
	 * null).equals("Error: unknown currency char code"), "Null error check"); }
	 */
	@Test
	public void calculateTest2() throws Exception {
		Assert.isTrue(currencyService.currencyCalculate(100.0, 1.0, 70.0, 1.0, 1.0).equals("7000.0"), "Correct example");
	}
}
