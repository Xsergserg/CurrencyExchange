package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrencyDataTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void incorrectStr1() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "100", String.class))
				.contains("Error: Wrong string format");
	}
	@Test
	public void incorrectStr2() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "100 Usd", String.class))
				.contains("Error: Wrong string format");
	}
	@Test
	public void incorrectStr3() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "100 Usd EUR asd", String.class))
				.contains("Error: Wrong string format");
	}
	@Test
	public void incorrectStr4() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "Usd EUR asd", String.class))
				.contains("Error: Wrong string format");
	}
	@Test
	public void incorrectStr5() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "-100 EUR asd", String.class))
				.contains("Error: Wrong string format");
	}
	@Test
	public void correctStr1() throws Exception {
		CurrencyData currencyData = new CurrencyData("100 EUR RUR");
		CurrencyData otherCurrencyData = new CurrencyData(100.0, "EUR", "RUR", true);
		Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example");
	}
	@Test
	public void correctStr2() throws Exception {
		CurrencyData currencyData = new CurrencyData("100,EUR,RUR");
		CurrencyData otherCurrencyData = new CurrencyData(100.0, "EUR", "RUR", true);
		Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example");
	}
	@Test
	public void correctStr3() throws Exception {
		CurrencyData currencyData = new CurrencyData("100 eur rur");
		CurrencyData otherCurrencyData = new CurrencyData(100.0, "EUR", "RUR", true);
		Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example");
	}
	@Test
	public void correctStr4() throws Exception {
		CurrencyData currencyData = new CurrencyData("100, amd rur");
		CurrencyData otherCurrencyData = new CurrencyData(100.0, "AMD", "RUR", true);
		Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example");
	}
	@Test
	public void correctStr5() throws Exception {
		CurrencyData currencyData = new CurrencyData("\"100 EUR RUR\"");
		CurrencyData otherCurrencyData = new CurrencyData(100.0, "EUR", "RUR", true);
		Assert.isTrue(currencyData.equals(otherCurrencyData), "Right example");
	}
}