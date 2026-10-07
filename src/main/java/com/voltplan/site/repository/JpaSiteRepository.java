package com.voltplan.site.repository;

import java.util.List;
import java.util.Optional;

import com.voltplan.site.Site;
import com.voltplan.site.SiteEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSiteRepository implements SiteRepository {

    private final SiteJpaRepository jpaRepository;

    public JpaSiteRepository(SiteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Site save(Site site) {
        SiteEntity entity = versEntity(site);
        SiteEntity sauvegarde = jpaRepository.save(entity);
        return versSite(sauvegarde);
    }

    @Override
    public Optional<Site> findById(Long id) {
        return jpaRepository.findById(id).map(this::versSite);
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public List<Site> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::versSite)
                .toList();
    }

    private Site versSite(SiteEntity entity) {
        return new Site(entity.getId(), entity.getCode(), entity.getNom(),
                entity.getFiliere(), entity.getRegion(), entity.getPuissanceInstalleeMw());
    }

    private SiteEntity versEntity(Site site) {
        return new SiteEntity(site.code(), site.nom(), site.filiere(),
                site.region(), site.puissanceInstalleeMw());
    }
}