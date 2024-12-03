package com.spribe.currency.persistance;

import com.spribe.currency.model.ExchangeRateEntity;
import com.spribe.currency.model.ExchangeRatePackEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRatePackRepository exchangeRatePackRepository;

    @Test
    public void testSaveExchangeRate() {
        ExchangeRatePackEntity pack = new ExchangeRatePackEntity();
        pack.setBaseCurrencyCode("USD");
        pack.setSuccess(true);
        pack.setFetchDate(LocalDateTime.now());
        pack.setApiTimestamp(System.currentTimeMillis());
        ExchangeRatePackEntity savedPack = exchangeRatePackRepository.save(pack);

        ExchangeRateEntity rate = new ExchangeRateEntity();
        rate.setCurrencyCode("EUR");
        rate.setRate(BigDecimal.valueOf(1.1));
        rate.setExchangeRatePack(savedPack);
        ExchangeRateEntity savedRate = exchangeRateRepository.save(rate);

        assertThat(savedRate).isNotNull();
        assertThat(savedRate.getId()).isNotNull();
        assertThat(savedRate.getCurrencyCode()).isEqualTo("EUR");
        assertThat(savedRate.getExchangeRatePack()).isEqualTo(savedPack);
    }

    @Test
    public void testFindById() {
        ExchangeRatePackEntity pack = new ExchangeRatePackEntity();
        pack.setBaseCurrencyCode("USD");
        pack.setSuccess(true);
        pack.setFetchDate(LocalDateTime.now());
        pack.setApiTimestamp(System.currentTimeMillis());
        ExchangeRatePackEntity savedPack = exchangeRatePackRepository.save(pack);

        ExchangeRateEntity rate = new ExchangeRateEntity();
        rate.setCurrencyCode("EUR");
        rate.setRate(BigDecimal.valueOf(1.1));
        rate.setExchangeRatePack(savedPack);
        ExchangeRateEntity savedRate = exchangeRateRepository.save(rate);

        Optional<ExchangeRateEntity> foundRate = exchangeRateRepository.findById(savedRate.getId());

        assertThat(foundRate).isPresent();
        assertThat(foundRate.get().getCurrencyCode()).isEqualTo("EUR");
        assertThat(foundRate.get().getExchangeRatePack()).isEqualTo(savedPack);
    }
}