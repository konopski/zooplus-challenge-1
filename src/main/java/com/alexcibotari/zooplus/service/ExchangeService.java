package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {

    Set<String> getCurrencies();

    Optional<Exchange> findOne(Long id);

    List<Exchange> findAll();

    List<Exchange> findAllByOwner(User user);

    List<Exchange> findAllForCurrentUser();

    Optional<Exchange> delete(Long id);

    Optional<Exchange> convert(ExchangeResource resource);

    Optional<Exchange> convert(String currency, BigDecimal amount, String date);
}
