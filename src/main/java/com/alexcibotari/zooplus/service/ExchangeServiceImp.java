package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.repository.ExchangeRepository;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;
import com.alexcibotari.zooplus.web.rest.resource.currencylayer.HistoricalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExchangeServiceImp implements ExchangeService {

    @Autowired
    private CurrencyLayerService currencyLayerService;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private UserService userService;

    public Set<String> getCurrencies() {
        return currencyLayerService.getCurrencies();
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
        HistoricalResource historical = currencyLayerService.getRateByDate(date);
        if (historical.getSuccess()) {
            String selectedRate = "USD" + currency;
            BigDecimal usdRate = historical.getQuotes().get(selectedRate);
            BigDecimal cvr = new BigDecimal(1).divide(usdRate, MathContext.DECIMAL128);
            Exchange exchange = new Exchange(currency, amount, date);
            Optional<User> user = userService.getCurrentUser();
            user.ifPresent(exchange::setOwner);

            user.ifPresent(u -> {
                if (exchangeRepository.countByOwner(u) > 9) {
                    exchangeRepository.findFirstByOwner(u).ifPresent(exchangeRepository::delete);
                }
            });

            exchange.setResult(historical.getQuotes().entrySet().stream()
                    .filter(map -> !map.getKey().equals(selectedRate))
                    .collect(Collectors.toMap(p -> p.getKey().substring(3), p -> cvr.multiply(amount).multiply(p.getValue()))));
            return Optional.ofNullable(exchangeRepository.save(exchange));
        }
        return Optional.empty();
    }

}
