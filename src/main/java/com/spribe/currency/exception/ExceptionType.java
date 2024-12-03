package com.spribe.currency.exception;

public enum ExceptionType {

    NO_DATA_FOR_BASE("NO_DATA_FOR_BASE", "There is no exchange rates fetched yet for base"),
    NO_DATA_FOR_TO("NO_DATA_FOR_BASE_TO", "There is no exchange rates fetched yet for base and to combination");

    private String code;
    private String description;

    ExceptionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
