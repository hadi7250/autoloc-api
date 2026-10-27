package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;

@Service("WEEKEND")
public class TarificationWeekend implements StrategieTarification {

    private static final double REDUCTION = 0.10;

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours * (1 - REDUCTION);
    }
}
