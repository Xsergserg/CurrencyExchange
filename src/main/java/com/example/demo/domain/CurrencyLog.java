package com.example.demo.domain;

import java.sql.Timestamp;

import javax.persistence.*;

import com.example.demo.dto.RequestParameters;

@Entity
@Table(name = "user_logs")
public class CurrencyLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private double value;

	@Column(name = "source_currency_char_code")
	private String sourceCurrencyCharCode;

	@Column(name = "target_currency_char_code")
	private String targetCurrencyCharCode;

	@Column
	private double result;

	@Column(name = "request_time")
	private Timestamp requestTime;

	public CurrencyLog(RequestParameters requestParameters, double result) {
		this.value = requestParameters.getValue();
		this.sourceCurrencyCharCode = requestParameters.getSourceCurrencyCharCode();
		this.targetCurrencyCharCode = requestParameters.getTargetCurrencyCharCode();
		this.result = result;
		this.requestTime = new Timestamp(System.currentTimeMillis());
	}
}
