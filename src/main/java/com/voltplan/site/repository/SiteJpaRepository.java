package com.voltplan.site.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.voltplan.site.Filiere;
import com.voltplan.site.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SiteJpaRepository extends JpaRepository<SiteEntity, Long> {

    boolean existsByCode(String code);

    Optional<SiteEntity> findByCode(String code);

    List<SiteEntity> findByFiliere(Filiere filiere);

    List<SiteEntity> findByFiliereAndRegion(Filiere filiere, String region);

    List<SiteEntity> findByNomContainingIgnoreCase(String motCle);

    List<SiteEntity> findByFiliereOrderByPuissanceInstalleeMwDesc(Filiere filiere);

    @Query("select s FROM SiteEntity s where s.puissanceInstalleeMw > :seuil")
    List<SiteEntity> trouverAuDelaDuSeuil(@Param("seuil") BigDecimal seuil);

    @Query("SELECT s.filiere AS filiere, COUNT(s) AS total FROM SiteEntity s GROUP BY s.filiere")
    List<Object[]> compterParFiliere();

}