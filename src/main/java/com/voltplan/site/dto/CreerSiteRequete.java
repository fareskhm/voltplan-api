package com.voltplan.site.dto;

import java.math.BigDecimal;

import com.voltplan.site.Filiere;
import com.voltplan.site.Site;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreerSiteRequete(
        @NotBlank(message = "Le code est obligatoire")
        @Size(max = 30, message = "Le code ne doit pas dépasser 30 caractères")
        String code,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @NotNull(message = "La filière est obligatoire")
        Filiere filiere,

        @NotBlank(message = "La région est obligatoire")
        String region,

        @NotNull(message = "La puissance installée est obligatoire")
        @Positive(message = "La puissance installée doit être strictement positive")
        BigDecimal puissanceInstalleeMw) {

    public Site versSite() {
        return new Site(null, code, nom, filiere, region, puissanceInstalleeMw);
    }
}