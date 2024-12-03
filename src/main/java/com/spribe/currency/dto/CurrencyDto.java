package com.spribe.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private Long id;
    private String code;
    private LocalDateTime creationDate;
}
