package com.voltplan.site.repository;

import com.voltplan.site.Site;

import java.util.List;
import java.util.Optional;


public interface SiteRepository {

    Site save(Site site);

    Optional<Site> findById(Long id);

    boolean existsByCode(String code);

    List<Site> findAll();
}