package com.voltplan.site;

import java.math.BigDecimal;

public record Site(
        Long id,
        String code,
        String nom,
        Filiere filiere,
        String region,
        BigDecimal puissanceInstalleeMw) {
}