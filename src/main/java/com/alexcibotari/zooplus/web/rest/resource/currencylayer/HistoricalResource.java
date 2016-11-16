package com.alexcibotari.zooplus.web.rest.resource.currencylayer;

import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.Map;

@Relation(value = "historical", collectionRelation = "historicals")
public class HistoricalResource {

    private Boolean success;
    private String terms;
    private String privacy;
    private Boolean historical;
    private String date;
    private Long timestamp;
    private String source;
    private Map<String, BigDecimal> quotes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Boolean getHistorical() {
        return historical;
    }

    public void setHistorical(Boolean historical) {
        this.historical = historical;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, BigDecimal> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, BigDecimal> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "HistoricalResource{" +
                "success=" + success +
                ", terms='" + terms + '\'' +
                ", privacy='" + privacy + '\'' +
                ", historical=" + historical +
                ", date='" + date + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", quotes=" + quotes +
                '}';
    }
}
