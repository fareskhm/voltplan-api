package com.voltplan.site;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SiteService {

    private final SiteRepository repository;

    public SiteService(SiteRepository repository) {
        this.repository = repository;
    }

    public Site creer(Site site) {
        if (repository.existsByCode(site.code())) {
            throw new SiteDejaExistantException(site.code());
        }
        return repository.save(site);
    }

    public Site trouverParId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SiteIntrouvableException(id));
    }

    public List<Site> lister(Filiere filiere) {
        List<Site> tous = repository.findAll();
        if (filiere == null) {
            return tous;
        }
        return tous.stream()
                .filter(site -> site.filiere() == filiere)
                .toList();
    }
}