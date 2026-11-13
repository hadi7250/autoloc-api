package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;
    private final JournalisationService journalisationService;

    @Override
    @Transactional
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = chargerEmploye(idEmploye);
        Agence ancienneAgence = employe.getAgence();
        if (ancienneAgence == null) {
            // Deja desaffecte : operation idempotente.
            return employe;
        }
        // Les deux cotes de l'association restent coherents : la FK (cote Employe) est cassee
        // et l'employe disparait de la collection de l'agence.
        ancienneAgence.getEmployes().remove(employe);
        employe.setAgence(null);
        Employe saved = employeRepository.save(employe);
        journalisationService.info("Employe desaffecte : id=" + idEmploye
                + ", ancienne agence id=" + ancienneAgence.getIdAgence());
        return saved;
    }

    @Override
    @Transactional
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = chargerEmploye(idEmploye);
        Agence agence = chargerAgence(idAgence);

        Agence agenceActuelle = employe.getAgence();
        if (agenceActuelle != null) {
            if (agenceActuelle.getIdAgence().equals(idAgence)) {
                // Deja dans cette agence : rien a faire.
                return employe;
            }
            // Un employe n'appartient qu'a une seule agence : le transfert exige une desaffectation.
            throw new IllegalArgumentException("L'employe " + idEmploye
                    + " est deja affecte a l'agence " + agenceActuelle.getIdAgence()
                    + " : une desaffectation est obligatoire avant tout transfert.");
        }

        employe.setAgence(agence);
        agence.getEmployes().add(employe);
        Employe saved = employeRepository.save(employe);
        journalisationService.info("Employe affecte : id=" + idEmploye + ", agence id=" + idAgence);
        return saved;
    }

    private Employe chargerEmploye(Long idEmploye) {
        if (idEmploye == null) {
            throw new IllegalArgumentException("L'identifiant de l'employe est obligatoire.");
        }
        return employeRepository.findById(idEmploye)
                .orElseThrow(() -> new RessourceIntrouvableException(
                        "Employe introuvable avec l'id : " + idEmploye));
    }

    private Agence chargerAgence(Long idAgence) {
        if (idAgence == null) {
            throw new IllegalArgumentException("L'identifiant de l'agence est obligatoire.");
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new RessourceIntrouvableException(
                        "Agence introuvable avec l'id : " + idAgence));
    }
}
