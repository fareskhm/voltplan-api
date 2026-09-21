package com.voltplan.site;

import java.math.BigDecimal;

public record CreerSiteRequete(
        String code,
        String nom,
        Filiere filiere,
        String region,
        BigDecimal puissanceInstalleeMw) {

    public Site versSite() {
        return new Site(null, code, nom, filiere, region, puissanceInstalleeMw);
    }
}