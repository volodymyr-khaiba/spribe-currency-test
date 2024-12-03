package com.spribe.currency.controller;

import com.spribe.currency.CurrencyApplication;
import com.spribe.currency.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurrencyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    public void testGetCurrencyRates() throws Exception {
        String currency = "EUR";
        Map<String, BigDecimal> rates = Collections.singletonMap("EUR", BigDecimal.valueOf(0.85));
        when(exchangeRateService.getRatesForBase(currency)).thenReturn(rates);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/exchange-rate/EUR/rates"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCurrencyRate() throws Exception {
        String base = "EUR";
        String to = "USD";
        BigDecimal rate = BigDecimal.valueOf(0.85);
        when(exchangeRateService.getRate(base, to)).thenReturn(rate);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/exchange-rate")
                        .param("base", base)
                        .param("to", to))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}