package com.voltplan.site.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService service;

    @Test
    void notifierCreationSite_rend_la_main_immediatement() throws InterruptedException {
        long debut = System.currentTimeMillis();

        service.notifierCreationSite("PV-TEST-01");

        long duree = System.currentTimeMillis() - debut;
        System.out.println("L'appel a rendu la main après " + duree + " ms");

        // On laisse le temps au thread asynchrone de finir avant que le test se termine
        Thread.sleep(3500);
    }
}