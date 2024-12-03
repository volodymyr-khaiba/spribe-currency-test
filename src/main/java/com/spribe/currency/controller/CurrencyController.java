package com.spribe.currency.controller;

import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDto>> getCurrency() {
        return ResponseEntity.ok(currencyService.listAllCurrencies());
    }

    @PostMapping()
    public ResponseEntity<CurrencyDto> createCurrency(@RequestBody CurrencyCreateDto currencyCreateDto) {
        CurrencyDto currency = currencyService.createCurrency(currencyCreateDto);
        return ResponseEntity.ok(currency);
    }

}