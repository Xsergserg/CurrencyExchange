package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.dto.RequestParameters;

@SpringBootTest
public class RequestParametersTest {

	@Test
	public void positiveValue() throws Exception {
		assertDoesNotThrow(()->{ new RequestParameters(100.0, "USD", "EUR");});
	}

	@Test
	public void zeroValue() throws Exception {
		assertDoesNotThrow(()->{ new RequestParameters(0.0, "USD", "EUR");});
	}

	@Test
	public void negativeValue() throws Exception {
		assertThrows(Exception.class, () -> {
			new RequestParameters(-100.0, "USD", "EUR");
		});
	}
}
