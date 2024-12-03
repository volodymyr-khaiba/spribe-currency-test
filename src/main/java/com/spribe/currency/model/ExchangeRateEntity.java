package com.spribe.currency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "EXCHANGE_RATE")
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "RATE")
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name="EXCHANGE_RATE_PACK_ID", nullable=false)
    private ExchangeRatePackEntity exchangeRatePack;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public ExchangeRatePackEntity getExchangeRatePack() {
        return exchangeRatePack;
    }

    public void setExchangeRatePack(ExchangeRatePackEntity exchangeRatePack) {
        this.exchangeRatePack = exchangeRatePack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateEntity that = (ExchangeRateEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
