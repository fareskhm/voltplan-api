package com.voltplan.site.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.voltplan.site.Filiere;
import com.voltplan.site.Site;

@SpringBootTest
@Transactional
class SiteServiceIntegrationTest {

    @Autowired
    private SiteService service;

    @Test
    void creer_puis_retrouver_un_site_avec_les_vrais_beans() {
        Site cree = service.creer(new Site(null, "EOL-BRETAGNE-01", "Parc éolien Bretagne",
                Filiere.EOLIEN, "Bretagne", new BigDecimal("48.0")));

        assertThat(cree.id()).isNotNull();
        assertThat(service.trouverParId(cree.id()).code()).isEqualTo("EOL-BRETAGNE-01");
    }
}