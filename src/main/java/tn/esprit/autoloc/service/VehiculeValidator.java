package tn.esprit.autoloc.service;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;

/**
 * Responsabilite unique : verifier les regles metier d'un vehicule avant persistance.
 * Ne sauvegarde rien, ne journalise rien.
 */
@Component
public class VehiculeValidator {

    public void valider(Vehicule vehicule) {
        if (vehicule == null) {
            throw new IllegalArgumentException("Le vehicule ne peut pas etre nul.");
        }
        if (vehicule.getTarifJournalier() == null
                || vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le tarif journalier doit etre strictement positif.");
        }
        if (estVide(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException("L'immatriculation est obligatoire.");
        }
        if (estVide(vehicule.getMarque())) {
            throw new IllegalArgumentException("La marque est obligatoire.");
        }
        if (estVide(vehicule.getModele())) {
            throw new IllegalArgumentException("Le modele est obligatoire.");
        }
        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException("La categorie est obligatoire.");
        }
        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException("Le statut est obligatoire.");
        }
    }

    private boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
