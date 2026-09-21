package com.voltplan.site;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SiteController.class)
class SiteControllerTest {

    private static final String CORPS_VALIDE = """
            {
              "code": "PV-LYON-01",
              "nom": "Parc solaire Lyon",
              "filiere": "SOLAIRE",
              "region": "Auvergne-Rhône-Alpes",
              "puissanceInstalleeMw": 12.5
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SiteService service;

    @Test
    void get_retourne_le_site_en_json_quand_il_existe() throws Exception {
        // Given
        Site site = new Site(7L, "PV-LYON-01", "Parc solaire Lyon", Filiere.SOLAIRE,
                "Auvergne-Rhône-Alpes", new BigDecimal("12.5"));
        when(service.trouverParId(7L)).thenReturn(site);

        // When / Then
        mockMvc.perform(get("/api/sites/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.code").value("PV-LYON-01"))
                .andExpect(jsonPath("$.filiere").value("SOLAIRE"))
                .andExpect(jsonPath("$.puissanceInstalleeMw").value(12.5));
    }

    @Test
    void get_retourne_404_quand_le_site_est_introuvable() throws Exception {
        // Given
        when(service.trouverParId(42L)).thenThrow(new SiteIntrouvableException(42L));

        // When / Then
        mockMvc.perform(get("/api/sites/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Aucun site avec l'identifiant 42"));
    }

    @Test
    void post_cree_le_site_et_retourne_201_avec_son_emplacement() throws Exception {
        // Given
        Site cree = new Site(1L, "PV-LYON-01", "Parc solaire Lyon", Filiere.SOLAIRE,
                "Auvergne-Rhône-Alpes", new BigDecimal("12.5"));
        when(service.creer(any(Site.class))).thenReturn(cree);

        // When / Then
        mockMvc.perform(post("/api/sites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPS_VALIDE))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/sites/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("PV-LYON-01"));
    }

    @Test
    void post_retourne_409_quand_le_code_existe_deja() throws Exception {
        // Given
        when(service.creer(any(Site.class)))
                .thenThrow(new SiteDejaExistantException("PV-LYON-01"));

        // When / Then
        mockMvc.perform(post("/api/sites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPS_VALIDE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Un site avec le code 'PV-LYON-01' existe déjà"));
    }
}