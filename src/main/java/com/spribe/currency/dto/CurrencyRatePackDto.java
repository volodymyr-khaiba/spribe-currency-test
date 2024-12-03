package com.spribe.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRatePackDto {

    private Boolean success;
    private Long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;

}
