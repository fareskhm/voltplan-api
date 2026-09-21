package com.voltplan.site;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SiteServiceIntegrationTest {

    @Autowired
    private SiteService service;

    @Test
    void creer_puis_retrouver_un_site_avec_les_vrais_beans() {
        // Given / When
        Site cree = service.creer(new Site(null, "EOL-BRETAGNE-01", "Parc éolien Bretagne",
                Filiere.EOLIEN, "Bretagne", new BigDecimal("48.0")));

        // Then
        assertThat(cree.id()).isNotNull();
        assertThat(service.trouverParId(cree.id()).code()).isEqualTo("EOL-BRETAGNE-01");
    }
}