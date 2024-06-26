package com.coding.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {AppProperties.class})
@EnableCaching
public class AppConfig {
}
