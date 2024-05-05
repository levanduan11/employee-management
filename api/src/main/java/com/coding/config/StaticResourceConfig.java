package com.coding.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class StaticResourceConfig {

    public static final String CLASSPATH_MESSAGES = "classpath:messages";
    public static final String UTF_8 = "UTF-8";

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        var messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename(CLASSPATH_MESSAGES);
        messageResource.setDefaultEncoding(UTF_8);
        return messageResource;
    }
}

