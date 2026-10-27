package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;

/**
 * Etape 4 de l'atelier : nouvelle politique ajoutee SANS modifier TarificationService.
 * Demonstration du principe OCP.
 */
@Service("ENTREPRISE")
public class TarificationEntreprise implements StrategieTarification {

    private static final double REDUCTION = 0.15;

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours * (1 - REDUCTION);
    }
}
