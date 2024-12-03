package com.spribe.currency.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.model.CurrencyEntity;
import com.spribe.currency.persistance.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    private CurrencyCreateDto currencyCreateDto;
    private CurrencyEntity currencyEntity;
    private CurrencyDto currencyDto;

    @BeforeEach
    public void setUp() {
        currencyCreateDto = new CurrencyCreateDto();
        currencyCreateDto.setCode("USD");

        currencyEntity = new CurrencyEntity();
        currencyEntity.setCode("USD");
        currencyEntity.setCreationDate(LocalDateTime.now());

        currencyDto = new CurrencyDto();
        currencyDto.setCode("USD");
    }

    @Test
    public void testCreateCurrency() {
        when(currencyRepository.save(any(CurrencyEntity.class))).thenReturn(currencyEntity);
        when(objectMapper.convertValue(any(CurrencyEntity.class), any(Class.class))).thenReturn(currencyDto);

        CurrencyDto result = currencyService.createCurrency(currencyCreateDto);

        assertEquals("USD", result.getCode());
    }

    @Test
    public void testListAllCurrencies() {
        when(currencyRepository.findAll()).thenReturn(List.of(currencyEntity));
        when(objectMapper.convertValue(any(CurrencyEntity.class), any(Class.class))).thenReturn(currencyDto);

        List<CurrencyDto> result = currencyService.listAllCurrencies();

        assertEquals(1, result.size());
        assertEquals("USD", result.get(0).getCode());
    }
}