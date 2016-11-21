package com.alexcibotari.zooplus.service;

import com.alexcibotari.zooplus.web.rest.resource.currencylayer.HistoricalResource;

import java.util.Set;

/**
 * Service for Currency Layer External Application
 */
public interface CurrencyLayerService {

    /**
     * Get all application configured currencies from properties : currencylayer.currencies
     * @return currencies
     */
    Set<String> getCurrencies();

    /**
     * Execute external call to Currency Layer to get Rate
     * @param date target date
     * @return historical ratio
     */
    HistoricalResource getRateByDate(String date);
}
