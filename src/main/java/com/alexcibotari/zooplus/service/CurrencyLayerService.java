package com.alexcibotari.zooplus.service;

import com.alexcibotari.zooplus.web.rest.resource.currencylayer.HistoricalResource;

import java.util.Set;

public interface CurrencyLayerService {

    Set<String> getCurrencies();

    HistoricalResource getRateByDate(String date);
}
