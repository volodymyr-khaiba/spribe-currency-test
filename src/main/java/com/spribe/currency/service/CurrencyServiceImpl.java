package com.spribe.currency.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.model.CurrencyEntity;
import com.spribe.currency.persistance.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "currency.profile.full", havingValue = "true", matchIfMissing = true)
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public CurrencyDto createCurrency(CurrencyCreateDto currencyCreateDto) {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setCode(currencyCreateDto.getCode());
        currency.setCreationDate(LocalDateTime.now());
        CurrencyEntity savedCurrency = currencyRepository.save(currency);
        return mapToCurrencyDto(savedCurrency);
    }

    public List<CurrencyDto> listAllCurrencies() {
        List<CurrencyEntity> currencyList = currencyRepository.findAll();
        return currencyList.stream()
                .map(this::mapToCurrencyDto)
                .toList();
    }

    private CurrencyDto mapToCurrencyDto(CurrencyEntity currency) {
        return objectMapper.convertValue(currency, CurrencyDto.class);
    }

}
