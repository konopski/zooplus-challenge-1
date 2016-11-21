package com.alexcibotari.zooplus.service;


import java.util.Map;

/**
 * Country Service
 */
public interface CountryService {

    /**
     * Get all countries
     * @return key - ISO 3166, value - Full name
     */
    Map<String, String> getAll();
}
