package com.alexcibotari.zooplus.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class Exchange extends AbstractAuditingEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false, precision = 50, scale = 14)
    private BigDecimal amount;
    @Column(nullable = false)
    private String date;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "key")
    @Column(name = "value", precision = 50, scale = 14)
    @CollectionTable(name = "exchange_result", joinColumns = @JoinColumn(name = "exchange_id"))
    private Map<String, BigDecimal> result;

    public Exchange() {
    }

    public Exchange(String currency, BigDecimal amount, String date) {
        this.currency = currency;
        this.amount = amount;
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    @Override
    public String toString() {
        return "Exchange{" +
                "id=" + getId() +
                ", owner=" + owner +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", result=" + result +
                '}';
    }
}
