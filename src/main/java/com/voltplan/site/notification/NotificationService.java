package com.voltplan.site.notification;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    public void notifierCreationSite(String code) {
        System.out.println("Début envoi notification pour " + code
                + " sur le thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);   // simule un envoi lent (3 secondes)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Notification envoyée pour " + code
                + " sur le thread " + Thread.currentThread().getName());
    }
}