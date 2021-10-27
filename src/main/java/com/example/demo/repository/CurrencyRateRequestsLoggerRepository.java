package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CurrencyLog;

@Repository
public interface CurrencyRateRequestsLoggerRepository extends JpaRepository<CurrencyLog, Long> {
}
