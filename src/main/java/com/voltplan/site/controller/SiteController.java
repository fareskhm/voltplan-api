package com.voltplan.site.controller;

import java.net.URI;
import java.util.List;

import com.voltplan.site.dto.CreerSiteRequete;
import com.voltplan.site.Filiere;
import com.voltplan.site.Site;
import com.voltplan.site.service.SiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService service;

    public SiteController(SiteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Site> lister(@RequestParam(required = false) Filiere filiere) {
        return service.lister(filiere);
    }

    @GetMapping("/{id}")
    public Site trouver(@PathVariable Long id) {
        return service.trouverParId(id);
    }

    @PostMapping
    public ResponseEntity<Site> creer(@Valid @RequestBody CreerSiteRequete requete) {
        Site cree = service.creer(requete.versSite());
        URI emplacement = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.id())
                .toUri();
        return ResponseEntity.created(emplacement).body(cree);
    }
}