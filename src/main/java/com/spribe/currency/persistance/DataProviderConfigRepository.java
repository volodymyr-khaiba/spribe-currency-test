package com.spribe.currency.persistance;

import com.spribe.currency.model.DataProviderConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DataProviderConfigRepository extends JpaRepository<DataProviderConfigEntity, Long> {

    @Query(value = "SELECT * FROM DATA_PROVIDER_CONFIG ORDER BY CREATION_DATE DESC LIMIT 1", nativeQuery = true)
    Optional<DataProviderConfigEntity> getLatestConfig();

}
