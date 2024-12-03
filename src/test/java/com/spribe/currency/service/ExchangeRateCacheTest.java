package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.exception.CurrencyRateApplicationException;
import com.spribe.currency.exception.ExceptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateCacheTest {

    private ExchangeRateCache exchangeRateCache;

    @BeforeEach
    public void setUp() {
        exchangeRateCache = new ExchangeRateCache();
    }

    @Test
    public void testGetRatesValidBase() {
        CurrencyRatePackDto ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);

        exchangeRateCache.putBaseRatesToCache(ratePackDto);

        Map<String, BigDecimal> result = exchangeRateCache.getRates("USD");
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(0.85), result.get("EUR"));
    }

    @Test
    public void testGetRatesInvalidBase() {
        Exception exception = assertThrows(CurrencyRateApplicationException.class, () -> {
            exchangeRateCache.getRates("INVALID");
        });

        assertEquals(ExceptionType.NO_DATA_FOR_BASE.getCode(), ((CurrencyRateApplicationException) exception).getCode());
    }

    @Test
    public void testGetRateValidPair() {
        CurrencyRatePackDto ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);

        exchangeRateCache.putBaseRatesToCache(ratePackDto);

        BigDecimal result = exchangeRateCache.getRate("USD", "EUR");
        assertEquals(BigDecimal.valueOf(0.85), result);
    }

    @Test
    public void testGetRateInvalidPair() {
        CurrencyRatePackDto ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);

        exchangeRateCache.putBaseRatesToCache(ratePackDto);

        Exception exception = assertThrows(CurrencyRateApplicationException.class, () -> {
            exchangeRateCache.getRate("USD", "INVALID");
        });

        assertEquals(ExceptionType.NO_DATA_FOR_TO.getCode(), ((CurrencyRateApplicationException) exception).getCode());
    }

    @Test
    public void testEvictCache() {
        CurrencyRatePackDto ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);

        exchangeRateCache.putBaseRatesToCache(ratePackDto);
        exchangeRateCache.evictCache();

        Exception exception = assertThrows(CurrencyRateApplicationException.class, () -> {
            exchangeRateCache.getRates("USD");
        });

        assertEquals(ExceptionType.NO_DATA_FOR_BASE.getCode(), ((CurrencyRateApplicationException) exception).getCode());
    }

    @Test
    public void testPutBaseRatesToCache() {
        CurrencyRatePackDto ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);

        exchangeRateCache.putBaseRatesToCache(ratePackDto);

        Map<String, BigDecimal> result = exchangeRateCache.getRates("USD");
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(0.85), result.get("EUR"));
    }

    @Test
    public void testConcurrentModification() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CurrencyRatePackDto ratePackDto1 = new CurrencyRatePackDto();
        ratePackDto1.setBase("USD");
        Map<String, BigDecimal> rates1 = new HashMap<>();
        rates1.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto1.setRates(rates1);

        CurrencyRatePackDto ratePackDto2 = new CurrencyRatePackDto();
        ratePackDto2.setBase("GBP");
        Map<String, BigDecimal> rates2 = new HashMap<>();
        rates2.put("USD", BigDecimal.valueOf(1.39));
        ratePackDto2.setRates(rates2);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> exchangeRateCache.putBaseRatesToCache(ratePackDto1));
            executorService.execute(() -> exchangeRateCache.putBaseRatesToCache(ratePackDto2));
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        Map<String, BigDecimal> usdRates = exchangeRateCache.getRates("USD");
        assertEquals(1, usdRates.size());
        assertEquals(BigDecimal.valueOf(0.85), usdRates.get("EUR"));

        Map<String, BigDecimal> gbpRates = exchangeRateCache.getRates("GBP");
        assertEquals(1, gbpRates.size());
        assertEquals(BigDecimal.valueOf(1.39), gbpRates.get("USD"));
    }
}