package com.voltplan.site;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    @Mock
    private SiteRepository repository;

    @InjectMocks
    private SiteService service;

    @Test
    void creer_sauvegarde_le_site_et_retourne_celui_qui_porte_l_id() {
        // Given
        Site aCreer = siteSansId("PV-LYON-01");
        Site sauvegarde = avecId(aCreer, 1L);
        when(repository.save(aCreer)).thenReturn(sauvegarde);

        // When
        Site resultat = service.creer(aCreer);

        // Then
        assertThat(resultat.id()).isEqualTo(1L);
        verify(repository).save(aCreer);
    }

    @Test
    void creer_refuse_un_code_deja_utilise() {
        // Given
        Site doublon = siteSansId("PV-LYON-01");
        when(repository.existsByCode("PV-LYON-01")).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> service.creer(doublon))
                .isInstanceOf(SiteDejaExistantException.class)
                .hasMessageContaining("PV-LYON-01");

        verify(repository, never()).save(any());
    }

    @Test
    void trouverParId_retourne_le_site_quand_il_existe() {
        // Given
        Site existant = avecId(siteSansId("PV-LYON-01"), 7L);
        when(repository.findById(7L)).thenReturn(Optional.of(existant));

        // When
        Site resultat = service.trouverParId(7L);

        // Then
        assertThat(resultat).isEqualTo(existant);
    }

    @Test
    void trouverParId_leve_une_exception_quand_le_site_est_absent() {
        // Given
        when(repository.findById(42L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> service.trouverParId(42L))
                .isInstanceOf(SiteIntrouvableException.class)
                .hasMessageContaining("42");
    }

    // --- Méthodes utilitaires de test ---

    private Site siteSansId(String code) {
        return new Site(null, code, "Parc solaire", Filiere.SOLAIRE,
                "Auvergne-Rhône-Alpes", new BigDecimal("12.5"));
    }

    private Site avecId(Site site, Long id) {
        return new Site(id, site.code(), site.nom(), site.filiere(),
                site.region(), site.puissanceInstalleeMw());
    }
}