package com.alexcibotari.zooplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@EnableCaching
public class CachingSetup implements JCacheManagerCustomizer {

    @Autowired
    CurrencyLayerProperties currencyLayerProperties;

    @Override
    public void customize(CacheManager cacheManager) {
        cacheManager.createCache("currency-layer", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(SECONDS, currencyLayerProperties.getTimeout())))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
    }
}
