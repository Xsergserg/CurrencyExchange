package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.example.demo.dto.RequestParameters;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestParametersParseTest {

	@Test
	public void incorrectRequests() throws Exception {
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("100");
		});
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("100 Usd");
		});
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("100 Usd EUR asd");
		});
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("Usd EUR asd");
		});
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("-100 EUR asd");
		});
		assertThrows(Exception.class, () -> {
			RequestParameters.parse("100, EUR, asd, a");
		});
	}
	@Test
	public void correctRequests() throws Exception {
		assertEquals(RequestParameters.parse("100 EUR RUR"), new RequestParameters(100.0, "EUR", "RUR"));
		assertEquals(RequestParameters.parse("100,EUR,RUR"), new RequestParameters(100.0, "EUR", "RUR"));
		assertEquals(RequestParameters.parse("100,EUR RUR"), new RequestParameters(100.0, "EUR", "RUR"));
		assertEquals(RequestParameters.parse("100 eur rur"), new RequestParameters(100.0, "EUR", "RUR"));
		assertEquals(RequestParameters.parse("100, amd rur"), new RequestParameters(100.0, "AMD", "RUR"));
		assertEquals(RequestParameters.parse("\"100 EUR RUR\""), new RequestParameters(100.0, "EUR", "RUR"));
		assertEquals(RequestParameters.parse("100 EUR , Gbp"), new RequestParameters(100.0, "EUR", "GBP"));
	}
}
/*

 * 
 * @Test public void correctStr1() throws Exception { RequestParameters
 * currencyData = new RequestParameters("100 EUR RUR"); RequestParameters
 * otherCurrencyData = new RequestParameters(100.0, "EUR", "RUR", true);
 * Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example"); }
 * 
 * @Test public void correctStr2() throws Exception { RequestParameters
 * currencyData = new RequestParameters("100,EUR,RUR"); RequestParameters
 * otherCurrencyData = new RequestParameters(100.0, "EUR", "RUR", true);
 * Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example"); }
 * 
 * @Test public void correctStr3() throws Exception { RequestParameters
 * currencyData = new RequestParameters("100 eur rur"); RequestParameters
 * otherCurrencyData = new RequestParameters(100.0, "EUR", "RUR", true);
 * Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example"); }
 * 
 * @Test public void correctStr4() throws Exception { RequestParameters
 * currencyData = new RequestParameters("100, amd rur"); RequestParameters
 * otherCurrencyData = new RequestParameters(100.0, "AMD", "RUR", true);
 * Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example"); }
 * 
 * @Test public void correctStr5() throws Exception { RequestParameters
 * currencyData = new RequestParameters("\"100 EUR RUR\""); RequestParameters
 * otherCurrencyData = new RequestParameters(100.0, "EUR", "RUR", true);
 * Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example"); } }
 */