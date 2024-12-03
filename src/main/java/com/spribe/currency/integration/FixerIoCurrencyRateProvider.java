package com.spribe.currency.integration;

import com.spribe.currency.dto.CurrencyRatePackDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service("fixerIoRateProvider")
@Slf4j
public class FixerIoCurrencyRateProvider implements CurrencyRateProvider {

    @Value("${currency.provider.fixerio.baseurl}")
    private String baseUrl;

    @Value("${currency.provider.fixerio.apikey}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public CurrencyRatePackDto getCurrencyRates(String base) {
        log.info("Rates fetching process started for base = {}", base);
        UriComponents uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("latest")
                .queryParam("base", base)
                .queryParam("access_key", apiKey)
                .build();
        CurrencyRatePackDto rates = restTemplate.getForObject(uri.toUri(), CurrencyRatePackDto.class);
        //symbols = GBP,JPY,EUR
        log.info("Rates fetching process finished for base = {}", base);
        return rates;
    }

    @Override
    public String getProviderName() {
        return "fixerIo";
    }

}
