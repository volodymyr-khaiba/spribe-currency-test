package com.spribe.currency.persistance;

import com.spribe.currency.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    @Query("SELECT c from CurrencyEntity c where c.code = :code")
    CurrencyEntity getCurrencyByCode(String code);

}
