package com.alexcibotari.zooplus.web.rest.resource;

import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.Map;

@Relation(value = "exchange", collectionRelation = "exchanges")
public class ExchangeResource extends AbstractAuditingResource {
    private String currency;
    private BigDecimal amount;
    private String date;
    private Map<String, BigDecimal> result;

    public ExchangeResource() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, BigDecimal> getResult() {
        return result;
    }

    public void setResult(Map<String, BigDecimal> result) {
        this.result = result;
    }
}
