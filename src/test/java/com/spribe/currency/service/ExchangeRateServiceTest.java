package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.model.ExchangeRatePackEntity;
import com.spribe.currency.persistance.ExchangeRatePackRepository;
import com.spribe.currency.persistance.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private ExchangeRatePackRepository exchangeRatePackRepository;

    @Mock
    private ExchangeRateCache cache;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    private CurrencyRatePackDto ratePackDto;

    @BeforeEach
    public void setUp() {
        ratePackDto = new CurrencyRatePackDto();
        ratePackDto.setBase("USD");
        ratePackDto.setTimestamp(System.currentTimeMillis());
        ratePackDto.setSuccess(true);
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("EUR", BigDecimal.valueOf(0.85));
        ratePackDto.setRates(rates);
    }

    @Test
    public void testGetRatesForBase() {
        when(cache.getRates("USD")).thenReturn(ratePackDto.getRates());

        Map<String, BigDecimal> result = exchangeRateService.getRatesForBase("USD");

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(0.85), result.get("EUR"));
        verify(cache, times(1)).getRates("USD");
    }

    @Test
    public void testGetRate() {
        when(cache.getRate("USD", "EUR")).thenReturn(BigDecimal.valueOf(0.85));

        BigDecimal result = exchangeRateService.getRate("USD", "EUR");

        assertEquals(BigDecimal.valueOf(0.85), result);
        verify(cache, times(1)).getRate("USD", "EUR");
    }

    @Test
    public void testSaveRatesData() {
        ExchangeRatePackEntity savedPack = new ExchangeRatePackEntity();
        savedPack.setId(1L);
        when(exchangeRatePackRepository.save(any(ExchangeRatePackEntity.class))).thenReturn(savedPack);

        exchangeRateService.saveRatesData(ratePackDto);

        verify(exchangeRatePackRepository, times(1)).save(any(ExchangeRatePackEntity.class));
        verify(exchangeRateRepository, times(1)).saveAll(any(List.class));
        verify(cache, times(1)).putBaseRatesToCache(ratePackDto);
    }
}