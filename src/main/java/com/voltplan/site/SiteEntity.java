package com.voltplan.site;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "site")
public class SiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 100)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Filiere filiere;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(name = "puissance_installee_mw", nullable = false, precision = 10, scale = 2)
    private BigDecimal puissanceInstalleeMw;

    @Column(name = "derniere_visite")
    private LocalDate derniereVisite;

    protected SiteEntity() {
    }

    public SiteEntity(String code, String nom, Filiere filiere, String region, BigDecimal puissanceInstalleeMw) {
        this.code = code;
        this.nom = nom;
        this.filiere = filiere;
        this.region = region;
        this.puissanceInstalleeMw = puissanceInstalleeMw;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getNom() { return nom; }
    public Filiere getFiliere() { return filiere; }
    public String getRegion() { return region; }
    public BigDecimal getPuissanceInstalleeMw() { return puissanceInstalleeMw; }
    public LocalDate getDerniereVisite() { return derniereVisite; }
}