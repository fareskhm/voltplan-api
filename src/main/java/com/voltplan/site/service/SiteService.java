package com.voltplan.site.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.voltplan.site.Filiere;
import com.voltplan.site.Site;
import com.voltplan.site.exeption.PuissanceInvalideException;
import com.voltplan.site.exeption.SiteDejaExistantException;
import com.voltplan.site.exeption.SiteIntrouvableException;
import com.voltplan.site.notification.NotificationService;
import com.voltplan.site.repository.SiteRepository;

@Service
public class SiteService {

    private final SiteRepository repository;
    private final Predicate<BigDecimal> puissanceValide;
    private final NotificationService notificationService;

    public SiteService(SiteRepository repository, Predicate<BigDecimal> puissanceValide,
                       NotificationService notificationService) {
        this.repository = repository;
        this.puissanceValide = puissanceValide;
        this.notificationService = notificationService;
    }

    public Site creer(Site site) {
        if (repository.existsByCode(site.code())) {
            throw new SiteDejaExistantException(site.code());
        }
        if (!puissanceValide.test(site.puissanceInstalleeMw())) {
            throw new PuissanceInvalideException(site.puissanceInstalleeMw());
        }
        Site cree = repository.save(site);
        notificationService.notifierCreationSite(cree.code());
        return cree;
    }

    @Cacheable(cacheNames = "sites", key = "#id")
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