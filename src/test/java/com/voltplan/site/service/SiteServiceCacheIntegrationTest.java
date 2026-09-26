package com.voltplan.site.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.voltplan.site.Filiere;
import com.voltplan.site.Site;

@SpringBootTest
class SiteServiceCacheIntegrationTest {

    @Autowired
    private SiteService service;

    @Test
    void trouverParId_est_plus_rapide_au_deuxieme_appel() {
        Site cree = service.creer(new Site(null, "PV-CACHE-01", "Parc test cache",
                Filiere.SOLAIRE, "Bretagne", new BigDecimal("10")));

        long debut1 = System.currentTimeMillis();
        service.trouverParId(cree.id());
        long premierAppel = System.currentTimeMillis() - debut1;

        long debut2 = System.currentTimeMillis();
        service.trouverParId(cree.id());
        long deuxiemeAppel = System.currentTimeMillis() - debut2;

        System.out.println("Premier appel : " + premierAppel + " ms");
        System.out.println("Deuxième appel : " + deuxiemeAppel + " ms");

        assertThat(deuxiemeAppel).isLessThan(100);
    }
}