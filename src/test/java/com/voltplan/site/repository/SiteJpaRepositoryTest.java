package com.voltplan.site.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.voltplan.site.Filiere;
import com.voltplan.site.SiteEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SiteJpaRepositoryTest {

    @Autowired
    private SiteJpaRepository repository;

    @Test
    void save_puis_findById_retrouve_le_meme_site() {
        SiteEntity aSauvegarder = new SiteEntity("PV-DATAJPA-01", "Parc test DataJpaTest",
                Filiere.SOLAIRE, "Bretagne", new BigDecimal("15.0"));

        SiteEntity sauvegarde = repository.save(aSauvegarder);

        Optional<SiteEntity> retrouve = repository.findById(sauvegarde.getId());
        assertThat(retrouve).isPresent();
        assertThat(retrouve.get().getCode()).isEqualTo("PV-DATAJPA-01");
    }

    @Test
    void existsByCode_retourne_vrai_quand_le_code_existe() {
        repository.save(new SiteEntity("PV-DATAJPA-02", "Parc test", Filiere.EOLIEN,
                "Normandie", new BigDecimal("20.0")));

        assertThat(repository.existsByCode("PV-DATAJPA-02")).isTrue();
        assertThat(repository.existsByCode("CODE-INEXISTANT")).isFalse();
    }

    @Test
    void findByCode_retrouve_le_site_par_son_code() {
        repository.save(new SiteEntity("PV-DATAJPA-03", "Parc test", Filiere.HYDRAULIQUE,
                "Occitanie", new BigDecimal("30.0")));

        Optional<SiteEntity> trouve = repository.findByCode("PV-DATAJPA-03");

        assertThat(trouve).isPresent();
        assertThat(trouve.get().getRegion()).isEqualTo("Occitanie");
    }

    @Test
    void findByFiliere_ne_retourne_que_la_bonne_filiere() {
        repository.save(new SiteEntity("PV-EX-01", "Solaire A", Filiere.SOLAIRE, "Occitanie", new BigDecimal("5")));
        repository.save(new SiteEntity("PV-EX-02", "Eolien A", Filiere.EOLIEN, "Bretagne", new BigDecimal("40")));

        List<SiteEntity> resultat = repository.findByFiliere(Filiere.SOLAIRE);

        assertThat(resultat).extracting(SiteEntity::getCode).contains("PV-EX-01");
        assertThat(resultat).extracting(SiteEntity::getCode).doesNotContain("PV-EX-02");
    }

    @Test
    void findByFiliereAndRegion_combine_les_deux_criteres() {
        repository.save(new SiteEntity("PV-EX-03", "Solaire Bretagne", Filiere.SOLAIRE, "Pays de la Loire", new BigDecimal("5")));
        repository.save(new SiteEntity("PV-EX-04", "Solaire Occitanie", Filiere.SOLAIRE, "Occitanie", new BigDecimal("5")));

        List<SiteEntity> resultat = repository.findByFiliereAndRegion(Filiere.SOLAIRE, "Pays de la Loire");

        assertThat(resultat).extracting(SiteEntity::getCode).containsExactly("PV-EX-03");
    }

    @Test
    void findByNomContainingIgnoreCase_trouve_malgre_la_casse() {
        repository.save(new SiteEntity("PV-EX-05", "Parc Solaire du Littoral", Filiere.SOLAIRE, "Bretagne", new BigDecimal("5")));

        List<SiteEntity> resultat = repository.findByNomContainingIgnoreCase("littoral");

        assertThat(resultat).extracting(SiteEntity::getCode).contains("PV-EX-05");
    }

    @Test
    void findByFiliereOrderByPuissanceInstalleeMwDesc_trie_du_plus_grand_au_plus_petit() {
        repository.save(new SiteEntity("PV-EX-06", "Petit", Filiere.EOLIEN, "Bretagne", new BigDecimal("10")));
        repository.save(new SiteEntity("PV-EX-07", "Grand", Filiere.EOLIEN, "Bretagne", new BigDecimal("90")));

        List<SiteEntity> resultat = repository.findByFiliereOrderByPuissanceInstalleeMwDesc(Filiere.EOLIEN);

        assertThat(resultat.get(0).getCode()).isEqualTo("PV-EX-07");
    }

    @Test
    void trouverAuDelaDuSeuil_ne_retourne_que_les_sites_puissants() {
        repository.save(new SiteEntity("PV-EX-08", "Petit", Filiere.SOLAIRE, "Occitanie", new BigDecimal("5")));
        repository.save(new SiteEntity("PV-EX-09", "Grand", Filiere.EOLIEN, "Bretagne", new BigDecimal("60")));

        List<SiteEntity> resultat = repository.trouverAuDelaDuSeuil(new BigDecimal("50"));

        assertThat(resultat).extracting(SiteEntity::getCode).contains("PV-EX-09");
        assertThat(resultat).extracting(SiteEntity::getCode).doesNotContain("PV-EX-08");
    }
}