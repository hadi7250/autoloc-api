package tn.esprit.autoloc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Responsabilite unique : journaliser les evenements metier.
 * Seul endroit a modifier si la destination des logs change (console -> fichier -> outil d'observabilite).
 */
@Service
public class JournalisationService {

    private static final Logger log = LoggerFactory.getLogger(JournalisationService.class);

    public void info(String message) {
        log.info(message);
    }

    public void avertissement(String message) {
        log.warn(message);
    }
}
