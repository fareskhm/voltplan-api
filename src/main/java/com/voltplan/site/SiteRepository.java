package com.voltplan.site;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface SiteRepository {

    Site save(Site site);

    Optional<Site> findById(Long id);

    boolean existsByCode(String code);

    List<Site> findAll();
}