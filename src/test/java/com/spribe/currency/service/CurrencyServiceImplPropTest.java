package com.spribe.currency.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "currency.profile.full=true")
@ActiveProfiles("test")
public class CurrencyServiceImplPropTest {

    @Autowired(required = true)
    private CurrencyService currencyService;


    @Test
    public void testCurrencyServiceBeanIsNotEUR() {
        assertNotNull(currencyService, "CurrencyService should be present");
        assertTrue(currencyService instanceof CurrencyServiceImpl, "CurrencyService should not be an instance of CurrencyServiceImpl");
    }

}