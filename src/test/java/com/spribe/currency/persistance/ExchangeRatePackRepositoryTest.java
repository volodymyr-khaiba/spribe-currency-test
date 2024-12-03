package com.spribe.currency.persistance;

import com.spribe.currency.model.ExchangeRatePackEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExchangeRatePackRepositoryTest {

    @Autowired
    private ExchangeRatePackRepository exchangeRatePackRepository;

    @Test
    public void testSaveExchangeRatePack() {
        ExchangeRatePackEntity pack = new ExchangeRatePackEntity();
        pack.setBaseCurrencyCode("USD");
        pack.setSuccess(true);
        pack.setFetchDate(LocalDateTime.now());
        pack.setApiTimestamp(System.currentTimeMillis());

        ExchangeRatePackEntity savedPack = exchangeRatePackRepository.save(pack);

        assertThat(savedPack).isNotNull();
        assertThat(savedPack.getId()).isNotNull();
        assertThat(savedPack.getBaseCurrencyCode()).isEqualTo("USD");
    }

    @Test
    public void testFindAll() {
        ExchangeRatePackEntity pack1 = new ExchangeRatePackEntity();
        pack1.setBaseCurrencyCode("USD");
        pack1.setSuccess(true);
        pack1.setFetchDate(LocalDateTime.now().minusDays(1));
        pack1.setApiTimestamp(System.currentTimeMillis() - 1000);
        exchangeRatePackRepository.save(pack1);

        ExchangeRatePackEntity pack2 = new ExchangeRatePackEntity();
        pack2.setBaseCurrencyCode("EUR");
        pack2.setSuccess(true);
        pack2.setFetchDate(LocalDateTime.now());
        pack2.setApiTimestamp(System.currentTimeMillis());
        exchangeRatePackRepository.save(pack2);

        List<ExchangeRatePackEntity> packs = exchangeRatePackRepository.findAll();

        assertThat(packs).hasSize(2);
        assertThat(packs).extracting(ExchangeRatePackEntity::getBaseCurrencyCode).containsExactlyInAnyOrder("USD", "EUR");
    }
}