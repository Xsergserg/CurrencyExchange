package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.dto.RequestParameters;
import com.example.demo.repository.CurrencyExchangeRepository;
import com.example.demo.service.CurrencyService;

@SpringBootTest
public class CurrencyExchangeTest {
	private CurrencyService currencyService;

	@Autowired
	public CurrencyExchangeTest(CurrencyService currencyService, CurrencyExchangeRepository currencyExchangeRepository) {
		this.currencyService = new CurrencyService(new CurrencyFromFileService(), currencyExchangeRepository);
	}

	@Test
	public void calculateTests1() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(100.0, "EUR", "RUR")), "8261.62");
	}

	@Test
	public void calculateTest2() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(100.0, "RUR", "EUR")), "1.2104163590191752");
	}

	@Test
	public void calculateTest3() throws Exception {
		assertEquals(currencyService.currencyExchange(new RequestParameters(1.0, "GBP", "USD")), "1.3784999050038353");
	}

	@Test
	public void NullExceptionTest() throws Exception {
		assertThrows(Exception.class, () -> {
			currencyService.currencyExchange(new RequestParameters(100.0, "EUR", null));
		});
	}
}
