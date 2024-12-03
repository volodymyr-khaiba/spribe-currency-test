package com.spribe.currency.integration;

import com.spribe.currency.dto.CurrencyRatePackDto;

public interface CurrencyRateProvider {

    String getProviderName();

    CurrencyRatePackDto getCurrencyRates(String base);

}
