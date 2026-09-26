package com.voltplan.site.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.voltplan.site.repository.SiteRepository;

@Component
public class SiteMonitoringTask {

    private static final Logger log = LoggerFactory.getLogger(SiteMonitoringTask.class);

    private final SiteRepository repository;

    public SiteMonitoringTask(SiteRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 10000)
    public void compterLesSites() {
        int nombre = repository.findAll().size();
        log.info("Surveillance : {} site(s) actuellement enregistré(s)", nombre);
    }
}