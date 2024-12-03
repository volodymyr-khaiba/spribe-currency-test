package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(name = "currency.profile.full", havingValue = "false", matchIfMissing = false)
public class CurrencyServiceEUR implements CurrencyService {

    private static CurrencyDto singleCurrencyDto;

    public CurrencyServiceEUR() {
        singleCurrencyDto = new CurrencyDto(-1L, "EUR", LocalDateTime.now());
    }

    @Override
    public CurrencyDto createCurrency(CurrencyCreateDto currencyCreateDto) {
        log.info("Current currency.profile is not full so application is working only with EUR base currency. " +
                "Switch currency.profile.full=true to be able to works with all currencies");
        return singleCurrencyDto;
    }

    @Override
    public List<CurrencyDto> listAllCurrencies() {
        log.info("Current currency.profile is not full so application is working only with EUR base currency. " +
                "Switch currency.profile.full=true to be able to works with all currencies");
        return List.of(singleCurrencyDto);
    }

}
