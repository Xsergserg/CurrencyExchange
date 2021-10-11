package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains(
				"Greetings from CurrencyExchangeApp!\n For using this app send string in form 'x, y, z', where x - ammount, y - source currency, z - target currency.\n For example: localhost:8080/\"100, USD, RUR\" or localhost:8080/str=\"100, USD, RUR\"");
	}
	@Test
	public void rurToRur() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + "100,rur,rur", String.class))
				.contains("100");
	}
}
