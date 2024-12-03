package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.integration.CurrencyRateProvider;
import com.spribe.currency.model.DataProviderConfigEntity;
import com.spribe.currency.persistance.DataProviderConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateFetcherServiceTest {
    @Mock
    private ExchangeRateService rateService;

    @Mock
    private DataProviderConfigRepository providerConfigRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private CurrencyRateProvider currencyRateProvider;

    private ExchangeRateFetcherService exchangeRateFetcherService;

    @BeforeEach
    void setUp() {
        when(currencyRateProvider.getProviderName()).thenReturn("fixerIo");
        List<CurrencyRateProvider> rateProviders = List.of(currencyRateProvider);
        exchangeRateFetcherService = new ExchangeRateFetcherService(
                rateProviders,
                rateService,
                providerConfigRepository,
                currencyService
        );
    }

    @Test
    void testFetchAndStoreExchangeRates() {
        // Mocking the required data
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCode("USD");
        List<CurrencyDto> currencyDtos = List.of(currencyDto);

        CurrencyRatePackDto ratePack = new CurrencyRatePackDto();

        when(currencyService.listAllCurrencies()).thenReturn(currencyDtos);
        DataProviderConfigEntity dataProviderConfigEntity = new DataProviderConfigEntity();
        dataProviderConfigEntity.setProviderName("fixerIo");
        when(providerConfigRepository.getLatestConfig()).thenReturn(Optional.of(dataProviderConfigEntity));
        when(currencyRateProvider.getCurrencyRates("USD")).thenReturn(ratePack);

        // Call the method to test
        exchangeRateFetcherService.fetchAndStoreExchangeRates();

        // Verify interactions
        verify(currencyService).listAllCurrencies();
        verify(providerConfigRepository).getLatestConfig();
        verify(currencyRateProvider).getCurrencyRates("USD");
        verify(rateService).saveRatesData(ratePack);
    }
}