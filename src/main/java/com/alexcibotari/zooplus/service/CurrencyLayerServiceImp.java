package com.alexcibotari.zooplus.service;

import com.alexcibotari.zooplus.config.CurrencyLayerProperties;
import com.alexcibotari.zooplus.web.rest.resource.currencylayer.HistoricalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyLayerServiceImp implements CurrencyLayerService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyLayerProperties currencyLayerProperties;
    private String currencies;
    private String historicalURL;

    @PostConstruct
    public void init() {
        this.currencies = currencyLayerProperties.getCurrencies().stream().collect(Collectors.joining(","));
        this.historicalURL = new StringBuilder()
                .append(this.currencyLayerProperties.getHost())
                .append("/historical?access_key=")
                .append(this.currencyLayerProperties.getKey())
                .append("&currencies=")
                .append(this.currencies)
                .append("&date={date}")
                .toString();
    }

    @Cacheable("currency-layer")
    public HistoricalResource getRateByDate(String date) {
        System.out.println("getRateByDate");
        return restTemplate.getForObject(this.historicalURL, HistoricalResource.class, date);
    }

    public Set<String> getCurrencies() {
        return currencyLayerProperties.getCurrencies();
    }

    private String getHistoricalURL() {
        return historicalURL;
    }
}
