package com.spribe.currency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EXCHANGE_RATE_PACK")
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRatePackEntity {
    //todo vokh add partitioning for this table by dates

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BASE_CURRENCY_CODE")
    private String baseCurrencyCode;   //todo index

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "FETCH_DATE")
    private LocalDateTime fetchDate;   //todo index

    @Column(name = "API_TIMESTAMP")
    private Long apiTimestamp;

    @OneToMany(mappedBy = "exchangeRatePack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeRateEntity> exchangeRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LocalDateTime getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(LocalDateTime fetchDate) {
        this.fetchDate = fetchDate;
    }

    public Long getApiTimestamp() {
        return apiTimestamp;
    }

    public void setApiTimestamp(Long apiTimestamp) {
        this.apiTimestamp = apiTimestamp;
    }

    public List<ExchangeRateEntity> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRateEntity> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRatePackEntity that = (ExchangeRatePackEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
