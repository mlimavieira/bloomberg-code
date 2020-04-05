package com.bloomberg.client.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class BaseConfiguration {

    @Value("${operation.cache.ttl-in-seconds:60}")
    private int operationCacheTTL;

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        CaffeineCache operations = buildCache("operations", ticker, operationCacheTTL);
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(operations));
        return manager;
    }

    private CaffeineCache buildCache(String name, Ticker ticker, int secondsToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
                .maximumSize(100)
                .ticker(ticker)
                .build());
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

}
