package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Contexte du patron Strategy.
 * Spring injecte ici TOUS les beans de type StrategieTarification, indexes par leur nom de bean.
 */
@Service
public class TarificationService {

    private final Map<String, StrategieTarification> strategies;

    public TarificationService(Map<String, StrategieTarification> strategies) {
        this.strategies = strategies;
    }

    public double calculer(String type, double tarifJournalier, int jours) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Le type de tarification est obligatoire.");
        }
        if (tarifJournalier <= 0) {
            throw new IllegalArgumentException("Le tarif journalier doit etre strictement positif.");
        }
        if (jours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit etre strictement positif.");
        }

        StrategieTarification strategie = strategies.get(type.trim().toUpperCase());
        if (strategie == null) {
            throw new IllegalArgumentException("Type de tarification inconnu : " + type
                    + ". Valeurs possibles : STANDARD, WEEKEND, LONGUE_DUREE, ENTREPRISE.");
        }
        return strategie.calculer(tarifJournalier, jours);
    }

    public java.util.Set<String> typesDisponibles() {
        return strategies.keySet();
    }
}
