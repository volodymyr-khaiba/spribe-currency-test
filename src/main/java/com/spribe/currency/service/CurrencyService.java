package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    CurrencyDto createCurrency(CurrencyCreateDto currencyCreateDto);

    List<CurrencyDto> listAllCurrencies();
}
