package com.voltplan.site.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.voltplan.site.Site;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySiteRepository implements SiteRepository {

    private final Map<Long, Site> stockage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Site save(Site site) {
        Long id = site.id() != null ? site.id() : sequence.incrementAndGet();
        Site enregistre = new Site(id, site.code(), site.nom(), site.filiere(),
                site.region(), site.puissanceInstalleeMw());
        stockage.put(id, enregistre);
        return enregistre;
    }

    @Override
    public Optional<Site> findById(Long id) {
        try {
            Thread.sleep(2000);   // simule un aller-retour vers une vraie base
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Optional.ofNullable(stockage.get(id));
    }

    @Override
    public boolean existsByCode(String code) {
        return stockage.values().stream().anyMatch(s -> s.code().equals(code));
    }

    @Override
    public List<Site> findAll() {
        return new ArrayList<>(stockage.values());
    }
}