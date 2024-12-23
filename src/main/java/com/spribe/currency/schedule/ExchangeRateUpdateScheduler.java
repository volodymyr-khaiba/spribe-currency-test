package com.spribe.currency.schedule;

import com.spribe.currency.service.ExchangeRateFetcherService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "scheduler.active", havingValue = "true", matchIfMissing = false)
public class ExchangeRateUpdateScheduler {

    @Autowired
    private ExchangeRateFetcherService exchangeRateFetcherService;

    @PostConstruct
    public void onStartup() {
        exchangeRateFetcherService.fetchAndStoreExchangeRates();
    }

    @Scheduled(cron = "${scheduler.cron.expression}")
    public void runCurrencyRateFetchProcess() {
        exchangeRateFetcherService.fetchAndStoreExchangeRates();
    }

}
