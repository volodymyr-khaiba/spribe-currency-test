package com.spribe.currency.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "currency.profile.full=false")
@ActiveProfiles("test")
public class CurrencyServiceEURPropTest {

    @Autowired(required = true)
    private CurrencyService currencyService;

    @Test
    public void testCurrencyServiceBeanIsEUR() {
        assertNotNull(currencyService, "CurrencyService should present");
        assertTrue(currencyService instanceof CurrencyServiceEUR, "CurrencyService should be an instance of CurrencyServiceEUR");
    }

}