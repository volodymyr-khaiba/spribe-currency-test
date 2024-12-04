package com.spribe.currency.controller;

import com.spribe.currency.exception.CurrencyRateApplicationException;
import com.spribe.currency.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/exchange-rate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/{currency}/rates")
    public ResponseEntity<Map<String, BigDecimal>> getCurrencyRates(@PathVariable String currency) {
        try {
            return ResponseEntity.ok(exchangeRateService.getRatesForBase(currency));
        } catch (CurrencyRateApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getCurrencyRate(
            @RequestParam String base,
            @RequestParam String to
    ) {
        try {
            return ResponseEntity.ok(exchangeRateService.getRate(base, to));
        } catch (CurrencyRateApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
