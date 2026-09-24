package com.voltplan.site.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "voltplan.site")
public record SitePropertes(BigDecimal puissanceMaxMw) {
}