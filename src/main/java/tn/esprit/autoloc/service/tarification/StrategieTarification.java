package tn.esprit.autoloc.service.tarification;

/**
 * Abstraction commune a toutes les politiques tarifaires (GoF Strategy).
 * Chaque implementation ne contient que sa formule.
 */
public interface StrategieTarification {

    double calculer(double tarifJournalier, int jours);
}
