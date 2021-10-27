package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.dto.RequestParameters;
import com.example.demo.repository.CurrencyRateRequestsLoggerRepository;
import com.example.demo.service.CurrencyService;

@SpringBootTest
public class CurrencyExchangeTest {
	private CurrencyService currencyService;

	@Autowired
	public CurrencyExchangeTest(CurrencyService currencyService, CurrencyRateRequestsLoggerRepository currencyRateRequestsLoggerRepository) {
		this.currencyService = new CurrencyService(new FileExchangeRateListRepository(), currencyRateRequestsLoggerRepository);
	}

	@Test
	public void calculateTests1() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(100.0, "EUR", "RUR")), "8500.0");
	}

	@Test
	public void calculateTest2() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(100.0, "RUR", "EUR")), "1.1764705882352942");
	}

	@Test
	public void calculateTest3() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(1.0, "EUR", "USD")), "1.1333333333333333");
	}

	@Test
	public void NullExceptionTest() throws Exception {
		assertThrows(Exception.class, () -> {
			currencyService.currencyExchange(new RequestParameters(100.0, "EUR", null));
		});
	}
}
