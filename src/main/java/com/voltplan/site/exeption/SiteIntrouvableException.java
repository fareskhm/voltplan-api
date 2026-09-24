package com.voltplan.site.exeption;

public class SiteIntrouvableException extends RuntimeException {

    public SiteIntrouvableException(Long id) {
        super("Aucun site avec l'identifiant " + id);
    }
}