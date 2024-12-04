package com.spribe.currency.service;

import com.spribe.currency.dto.CurrencyRatePackDto;
import com.spribe.currency.model.ExchangeRateEntity;
import com.spribe.currency.model.ExchangeRatePackEntity;
import com.spribe.currency.persistance.ExchangeRatePackRepository;
import com.spribe.currency.persistance.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRatePackRepository exchangeRatePackRepository;

    @Autowired
    private ExchangeRateCache cache;

    public Map<String, BigDecimal> getRatesForBase(String base) {
        return cache.getRates(base);
    }

    public BigDecimal getRate(String base, String to) {
        return cache.getRate(base, to);
    }

    @Transactional
    public void saveRatesData(CurrencyRatePackDto ratePackDto) {
        ExchangeRatePackEntity exchangeRatePackEntity = new ExchangeRatePackEntity();
        exchangeRatePackEntity.setApiTimestamp(ratePackDto.getTimestamp());
        exchangeRatePackEntity.setBaseCurrencyCode(ratePackDto.getBase());
        exchangeRatePackEntity.setSuccess(ratePackDto.getSuccess());
        exchangeRatePackEntity.setFetchDate(LocalDateTime.now());
        ExchangeRatePackEntity savedPack = exchangeRatePackRepository.save(exchangeRatePackEntity);

        if (!ratePackDto.getSuccess()) {
            log.error("Rates fetching failed for base = {} cache contains outdated data", ratePackDto.getBase());
            return;
        }

        List<ExchangeRateEntity> exchangeRateEntities = ratePackDto.getRates().entrySet()
                .stream()
                .map(e -> {
                    ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
                    exchangeRateEntity.setExchangeRatePack(savedPack);
                    exchangeRateEntity.setCurrencyCode(e.getKey());
                    exchangeRateEntity.setRate(e.getValue());
                    return exchangeRateEntity;
                }).toList();
        exchangeRateRepository.saveAll(exchangeRateEntities);
        cache.putBaseRatesToCache(ratePackDto);

        log.info("Rates storing finished for base = {}", ratePackDto.getBase());
    }

}
