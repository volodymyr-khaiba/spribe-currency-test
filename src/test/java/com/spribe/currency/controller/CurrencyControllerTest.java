package com.spribe.currency.controller;

import com.spribe.currency.CurrencyApplication;
import com.spribe.currency.dto.CurrencyCreateDto;
import com.spribe.currency.dto.CurrencyDto;
import com.spribe.currency.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurrencyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @Test
    public void testGetCurrency() throws Exception {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCode("USD");
        when(currencyService.listAllCurrencies()).thenReturn(Collections.singletonList(currencyDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currency/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());
    }

    @Test
    public void testCreateCurrency() throws Exception {
        CurrencyCreateDto currencyCreateDto = new CurrencyCreateDto();
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCode("USD");
        when(currencyService.createCurrency(currencyCreateDto)).thenReturn(currencyDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"USD\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("USD"));
    }
}