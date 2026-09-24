package com.voltplan.site.config;

import java.math.BigDecimal;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteConfiguration {

    @Value("${voltplan.site.puissance-max-mw}")
    private BigDecimal puissanceMaxMw;

    @Bean
    public Predicate<BigDecimal> puissanceValide() {
        return puissance -> puissance.compareTo(puissanceMaxMw) <= 0;
    }
}