package com.spribe.currency.persistance;

import com.spribe.currency.model.CurrencyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testSaveCurrency() {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setCode("USD");
        currency.setCreationDate(LocalDateTime.now());

        CurrencyEntity savedCurrency = currencyRepository.save(currency);

        assertThat(savedCurrency).isNotNull();
        assertThat(savedCurrency.getId()).isNotNull();
        assertThat(savedCurrency.getCode()).isEqualTo("USD");
    }

    @Test
    public void testFindCurrencyByCode() {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setCode("BGN");
        currency.setCreationDate(LocalDateTime.now());
        currencyRepository.save(currency);

        CurrencyEntity foundCurrency = currencyRepository.getCurrencyByCode("BGN");

        assertThat(foundCurrency).isNotNull();
        assertThat(foundCurrency.getCode()).isEqualTo("BGN");
    }
}