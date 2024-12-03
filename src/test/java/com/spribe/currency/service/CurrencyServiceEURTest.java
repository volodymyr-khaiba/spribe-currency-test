package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceEURTest {

    @InjectMocks
    private CurrencyServiceEUR currencyService;

    private CurrencyCreateDto currencyCreateDto;
    private CurrencyDto currencyDto;

    @BeforeEach
    public void setUp() {
        currencyCreateDto = new CurrencyCreateDto();
        currencyCreateDto.setCode("EUR");

        currencyDto = new CurrencyDto();
        currencyDto.setCode("EUR");
    }

    @Test
    public void testCreateCurrency() {
        CurrencyDto result = currencyService.createCurrency(currencyCreateDto);

        assertEquals("EUR", result.getCode());
    }

    @Test
    public void testListAllCurrencies() {
        List<CurrencyDto> result = currencyService.listAllCurrencies();

        assertEquals(1, result.size());
        assertEquals("EUR", result.get(0).getCode());
    }

    @Test
    public void testCreateCurrencyReturnsSameInstance() {
        CurrencyDto result1 = currencyService.createCurrency(currencyCreateDto);
        CurrencyDto result2 = currencyService.createCurrency(currencyCreateDto);

        assertEquals(result1, result2);
        assertSame(result1, result2);
    }
}