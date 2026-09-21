package com.voltplan.web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.voltplan.site.SiteDejaExistantException;
import com.voltplan.site.SiteIntrouvableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SiteIntrouvableException.class)
    public ProblemDetail siteIntrouvable(SiteIntrouvableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SiteDejaExistantException.class)
    public ProblemDetail siteDejaExistant(SiteDejaExistantException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail donneesInvalides(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> erreurs.putIfAbsent(e.getField(), e.getDefaultMessage()));

        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Les données envoyées sont invalides");
        probleme.setProperty("erreurs", erreurs);
        return probleme;
    }
}