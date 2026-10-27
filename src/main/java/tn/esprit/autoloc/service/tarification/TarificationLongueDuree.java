package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;

@Service("LONGUE_DUREE")
public class TarificationLongueDuree implements StrategieTarification {

    private static final double REDUCTION = 0.20;

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours * (1 - REDUCTION);
    }
}
