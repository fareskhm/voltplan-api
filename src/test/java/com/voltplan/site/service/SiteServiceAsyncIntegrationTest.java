package com.voltplan.site.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.voltplan.site.Filiere;
import com.voltplan.site.Site;

@SpringBootTest
class SiteServiceAsyncIntegrationTest {

    @Autowired
    private SiteService service;

    @Test
    void creer_repond_sans_attendre_la_fin_de_la_notification() {
        String codeUnique = "PV-ASYNC-" + System.currentTimeMillis();
        Site aCreer = new Site(null, codeUnique, "Parc test async",
                Filiere.SOLAIRE, "Bretagne", new BigDecimal("10"));

        long debut = System.currentTimeMillis();
        Site cree = service.creer(aCreer);
        long duree = System.currentTimeMillis() - debut;

        assertThat(cree.id()).isNotNull();
        assertThat(duree).isLessThan(1000);
    }
}