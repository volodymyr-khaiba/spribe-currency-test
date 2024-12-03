package com.spribe.currency.persistance;

import com.spribe.currency.model.ExchangeRatePackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRatePackRepository extends JpaRepository<ExchangeRatePackEntity, Long> {

}
