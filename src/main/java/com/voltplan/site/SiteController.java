package com.voltplan.site;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService service;

    public SiteController(SiteService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Site trouver(@PathVariable Long id) {
        return service.trouverParId(id);
    }

    @PostMapping
    public ResponseEntity<Site> creer(@RequestBody CreerSiteRequete requete) {
        Site cree = service.creer(requete.versSite());
        URI emplacement = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.id())
                .toUri();
        return ResponseEntity.created(emplacement).body(cree);
    }
}