package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;

@Service("STANDARD")
public class TarificationStandard implements StrategieTarification {

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours;
    }
}
