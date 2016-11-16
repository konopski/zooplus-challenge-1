package com.alexcibotari.zooplus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties("currencylayer")
public class CurrencyLayerProperties {

    private String key;
    private String host;
    private Set<String> currencies = new HashSet<>();
    //Default value 1 hour
    private Integer timeout = 3600;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Set<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<String> currencies) {
        this.currencies = currencies;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "CurrencyLayerProperties{" +
                "key='" + key + '\'' +
                ", host='" + host + '\'' +
                ", currencies=" + currencies +
                ", timeout=" + timeout +
                '}';
    }
}
