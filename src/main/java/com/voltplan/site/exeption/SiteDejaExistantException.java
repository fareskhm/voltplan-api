package com.voltplan.site.exeption;

public class SiteDejaExistantException extends RuntimeException {
    public SiteDejaExistantException(String code) {
        super("Un site avec le code '" + code + "' existe déjà");
    }
}
