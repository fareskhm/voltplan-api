package com.voltplan.site.exeption;

import java.math.BigDecimal;

public class PuissanceInvalideException extends RuntimeException {

    public PuissanceInvalideException(BigDecimal puissance) {
        super("Puissance installée invalide : " + puissance + " MW");
    }
}