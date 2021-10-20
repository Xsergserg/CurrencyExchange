package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.domain.Currency;
import com.example.demo.dto.CurrencyList;
import com.example.demo.service.CurrencyService;

@SpringBootTest
public class CurrencyExchangeTest {
	@Autowired
	private CurrencyService currencyService;

	ArrayList<Currency> currencyTest = new ArrayList<Currency>(Arrays.asList(new Currency("USD", 1.0, 70.96),
			new Currency("EUR", 1.0, 82.64), new Currency("RUR", 1.0, 1.0)));
	CurrencyList currencyList = new CurrencyList(currencyTest);

	@Test
	public void calculateTests1() throws Exception {
		Currency sourceCurrency = currencyList.getCurrencyByCharCode("USD");
		Currency targetCurrency = currencyList.getCurrencyByCharCode("EUR");
		assertEquals(
				currencyService.currencyCalculate(100.0, sourceCurrency.getNominal(), sourceCurrency.getValue(),
						targetCurrency.getNominal(), targetCurrency.getValue()), "85.86640851887705");
	}

	@Test
	public void calculateTest2() throws Exception {
		Currency sourceCurrency = currencyList.getCurrencyByCharCode("RUR");
		Currency targetCurrency = currencyList.getCurrencyByCharCode("EUR");
		assertEquals(
				currencyService.currencyCalculate(100.0, sourceCurrency.getNominal(), sourceCurrency.getValue(),
						targetCurrency.getNominal(), targetCurrency.getValue()), "1.2100677637947725");
	}

	@Test
	public void calculateTest3() throws Exception {
		Currency sourceCurrency = currencyList.getCurrencyByCharCode("EUR");
		Currency targetCurrency = currencyList.getCurrencyByCharCode("RUR");
		assertEquals(
				currencyService.currencyCalculate(100.0, sourceCurrency.getNominal(), sourceCurrency.getValue(),
						targetCurrency.getNominal(), targetCurrency.getValue()), "8264.0");
	}
	@Test
	public void NullExceptionTest() throws Exception {
		assertThrows(Exception.class, () -> {
				currencyService.currencyCalculate(null, null, null,null, null);
				});
	}
}
