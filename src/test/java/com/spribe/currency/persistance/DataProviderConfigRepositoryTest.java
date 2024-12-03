package com.spribe.currency.persistance;

import com.spribe.currency.model.DataProviderConfigEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataProviderConfigRepositoryTest {

    @Autowired
    private DataProviderConfigRepository dataProviderConfigRepository;

    @Test
    public void testSaveDataProviderConfig() {
        DataProviderConfigEntity config = new DataProviderConfigEntity();
        config.setProviderName("TestProvider");
        config.setCreationDate(LocalDateTime.now());

        DataProviderConfigEntity savedConfig = dataProviderConfigRepository.save(config);

        assertThat(savedConfig).isNotNull();
        assertThat(savedConfig.getId()).isNotNull();
        assertThat(savedConfig.getProviderName()).isEqualTo("TestProvider");
    }

    @Test
    public void testGetLatestConfig() {
        DataProviderConfigEntity config1 = new DataProviderConfigEntity();
        config1.setProviderName("Provider1");
        config1.setCreationDate(LocalDateTime.now().minusDays(1));
        dataProviderConfigRepository.save(config1);

        DataProviderConfigEntity config2 = new DataProviderConfigEntity();
        config2.setProviderName("Provider2");
        config2.setCreationDate(LocalDateTime.now());
        dataProviderConfigRepository.save(config2);

        Optional<DataProviderConfigEntity> latestConfig = dataProviderConfigRepository.getLatestConfig();

        assertThat(latestConfig).isPresent();
        assertThat(latestConfig.get().getProviderName()).isEqualTo("Provider2");
    }
}