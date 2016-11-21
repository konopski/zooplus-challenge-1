package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Exchange Service
 */
public interface ExchangeService {

    /**
     * Get all available currencies
     * @return currencies
     */
    Set<String> getCurrencies();

    /**
     * Find Exchange by ID
     * @param id
     * @return Optional Exchange
     */
    Optional<Exchange> findOne(Long id);

    /**
     * GEt all Exchanges
     * @return
     */
    List<Exchange> findAll();

    /**
     * GEt all Exchanges by User
     * @param user
     * @return
     */
    List<Exchange> findAllByOwner(User user);

    /**
     * GEt all Exchanges for current user
     * @return
     */
    List<Exchange> findAllForCurrentUser();

    /**
     * Delete Exchange by UD
     * @param id
     * @return deleted Exchange
     */
    Optional<Exchange> delete(Long id);

    /**
     * Get Exchange according to configuration
     * @param resource Configuration Resource
     * @return
     */
    Optional<Exchange> convert(ExchangeResource resource);

    /**
     * Get Exchange according to configuration
     * @param currency currency type
     * @param amount amount
     * @param date exchange date
     * @return
     */
    Optional<Exchange> convert(String currency, BigDecimal amount, String date);
}
