package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.config.CurrencyLayerProperties;
import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.repository.ExchangeRepository;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;
import com.alexcibotari.zooplus.web.rest.resource.currencylayer.HistoricalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExchangeServiceImp implements ExchangeService {

    @Autowired
    private CurrencyLayerProperties currencyLayerProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private UserService userService;

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

    public Set<String> getCurrencies() {
        return currencyLayerProperties.getCurrencies();
    }

    public Optional<Exchange> findOne(Long id) {
        return exchangeRepository.findOneById(id);
    }

    public List<Exchange> findAll() {
        return (List<Exchange>) exchangeRepository.findAll();
    }

    public List<Exchange> findAllByOwner(User user) {
        return exchangeRepository.findAllByOwner(user);
    }

    public List<Exchange> findAllForCurrentUser() {
        return userService.getCurrentUser().map(user -> exchangeRepository.findAllByOwner(user)).orElse(new ArrayList<>());
    }

    @Transactional
    public Optional<Exchange> delete(Long id) {
        return exchangeRepository.deleteOneById(id);
    }

    @Transactional
    public Optional<Exchange> convert(ExchangeResource resource) {
        return convert(resource.getCurrency(), resource.getAmount(), resource.getDate());
    }

    @Transactional
    public Optional<Exchange> convert(String currency, BigDecimal amount, String date) {
        HistoricalResource historical = restTemplate.getForObject(this.historicalURL, HistoricalResource.class, date);
        if (historical.getSuccess()) {
            String selectedRate = "USD" + currency;
            BigDecimal usdRate = historical.getQuotes().get(selectedRate);
            BigDecimal cvr = new BigDecimal(1).divide(usdRate, MathContext.DECIMAL128);
            Exchange exchange = new Exchange(currency, amount, date);
            userService.getCurrentUser().ifPresent(exchange::setOwner);
            exchange.setResult(historical.getQuotes().entrySet().stream()
                    .filter(map -> !map.getKey().equals(selectedRate))
                    .map(map -> {
                        map.setValue(map.getValue().multiply(cvr).multiply(amount));
                        return map;
                    })
                    .collect(Collectors.toMap(p -> p.getKey().substring(3), Map.Entry::getValue)));
            return Optional.ofNullable(exchangeRepository.save(exchange));
        }
        return Optional.empty();
    }

}
