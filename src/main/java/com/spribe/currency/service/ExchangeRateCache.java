package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.exception.CurrencyRateApplicationException;
import com.spribe.currency.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ExchangeRateCache {

    // base to currency and rate
    private ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> exchangeRates = new ConcurrentHashMap<>();

    public Map<String, BigDecimal> getRates(String base) {
        Map<String, BigDecimal> baseRates = exchangeRates.get(base);
        if (Objects.isNull(baseRates)) {
            log.error("unable to ret rates for {} base", base);
            throw new CurrencyRateApplicationException(
                    String.format("Unable to found rates for base = %s", base), ExceptionType.NO_DATA_FOR_BASE);
        }
        return baseRates;
    }

    public BigDecimal getRate(String base, String to) {
        Map<String, BigDecimal> baseRates = getRates(base);
        BigDecimal rate = baseRates.get(to);
        if (Objects.isNull(rate)) {
            log.error("unable to ret rates for base {} to {} pair", base, to);
            throw new CurrencyRateApplicationException(
                    String.format("Unable to found rate for base = %s and to = %s", base, to), ExceptionType.NO_DATA_FOR_TO);
        }
        return rate;
    }

    public void evictCache() {
        exchangeRates.clear();
    }

    public void putBaseRatesToCache(CurrencyRatePackDto ratePackDto) {
        ConcurrentHashMap<String, BigDecimal> baseRates = new ConcurrentHashMap<>();
        baseRates.putAll(ratePackDto.getRates());
        this.exchangeRates.put(ratePackDto.getBase(), baseRates);
        log.info("Cache was updated for base = {}", ratePackDto.getBase());
    }

}
