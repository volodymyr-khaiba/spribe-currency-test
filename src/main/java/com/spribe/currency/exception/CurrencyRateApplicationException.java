package com.spribe.currency.exception;

import lombok.Getter;

@Getter
public class CurrencyRateApplicationException extends RuntimeException {

    private String code;
    private String description;

    public CurrencyRateApplicationException(String message, ExceptionType exceptionType) {
        super(message);
        this.code = exceptionType.getCode();
        this.description = exceptionType.getDescription();
    }

}
