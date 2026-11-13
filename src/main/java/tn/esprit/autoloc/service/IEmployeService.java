package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {

    /**
     * Detache l'employe de son agence courante.
     */
    Employe desaffecterEmploye(Long idEmploye);

    /**
     * Rattache l'employe a une agence. Un employe ne peut appartenir qu'a une seule
     * agence : s'il est deja affecte ailleurs, une desaffectation est obligatoire.
     */
    Employe affecterEmploye(Long idEmploye, Long idAgence);
}
