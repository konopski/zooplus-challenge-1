package com.alexcibotari.zooplus.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CountryServiceImp implements CountryService {

    @Override
    public Map<String, String> getAll() {
        return Arrays.stream(Locale.getISOCountries()).sorted().map(code -> new Locale("", code)).collect(Collectors.toMap(Locale::getCountry, Locale::getDisplayCountry));
    }
}
