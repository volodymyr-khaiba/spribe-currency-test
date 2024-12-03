package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.integration.CurrencyRateProvider;
import com.spribe.currency.persistance.DataProviderConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExchangeRateFetcherService {

    @Value("${default.currency.provider.code}")
    String defaultProviderName;

    private final Map<String, CurrencyRateProvider> ratesProvider;

    private final ExchangeRateService rateService;

    private final DataProviderConfigRepository providerConfigRepository;

    private final CurrencyService currencyService;

    @Autowired
    public ExchangeRateFetcherService(
            List<CurrencyRateProvider> rateProviders,
            ExchangeRateService rateService,
            DataProviderConfigRepository providerConfigRepository,
            CurrencyService currencyService
    ) {
        ratesProvider = rateProviders.stream()
                .collect(Collectors.toMap(CurrencyRateProvider::getProviderName, Function.identity(), (a, b) -> a));
        this.rateService = rateService;
        this.providerConfigRepository = providerConfigRepository;
        this.currencyService = currencyService;
    }

    public void fetchAndStoreExchangeRates() {
        log.info("Process of exchange rates fetching is started");
        List<CurrencyDto> currencyDtos = currencyService.listAllCurrencies();
        log.info("Exchange rates will be fetched for next currencies = {}", currencyDtos);
        String rateProvider = providerConfigRepository.getLatestConfig()
                .map(pc -> pc.getProviderName())
                .orElse(defaultProviderName);
        currencyDtos.stream().forEach(currencyDto -> {
            fetchAndStoreExchangeRatesForCurrencyAndProvider(currencyDto.getCode(), rateProvider);
        });
        log.info("Process of exchange rates fetching finished");
    }

    public void fetchAndStoreExchangeRatesForCurrency(String currency) {
        String rateProvider = providerConfigRepository.getLatestConfig()
                .map(pc -> pc.getProviderName())
                .orElse(defaultProviderName);
        fetchAndStoreExchangeRatesForCurrencyAndProvider(currency, rateProvider);
    }

    private void fetchAndStoreExchangeRatesForCurrencyAndProvider(String currency, String provider) {
        log.info("Exchange rate will be fetched for next currency = {}", currency);
        CurrencyRatePackDto ratePack = ratesProvider.get(provider).getCurrencyRates(currency);
        rateService.saveRatesData(ratePack);
        log.info("Process of exchange rate fetching finished");
    }

}
