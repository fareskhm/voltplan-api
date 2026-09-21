package com.voltplan.web;

import com.voltplan.site.SiteDejaExistantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}