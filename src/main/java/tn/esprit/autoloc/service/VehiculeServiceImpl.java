package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;
    private final VehiculeValidator vehiculeValidator;
    private final JournalisationService journalisationService;

    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule retrieveVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException(
                        "Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public Vehicule addVehicule(Vehicule v) {
        vehiculeValidator.valider(v);
        Vehicule saved = vehiculeRepository.save(v);
        journalisationService.info("Vehicule ajoute : id=" + saved.getIdVehicule());
        return saved;
    }

    @Override
    public Vehicule modifyVehicule(Vehicule v) {
        if (v.getIdVehicule() == null || !vehiculeRepository.existsById(v.getIdVehicule())) {
            throw new RessourceIntrouvableException(
                    "Impossible de modifier : vehicule introuvable (id " + v.getIdVehicule() + ").");
        }
        vehiculeValidator.valider(v);
        Vehicule saved = vehiculeRepository.save(v);
        journalisationService.info("Vehicule modifie : id=" + saved.getIdVehicule());
        return saved;
    }

    @Override
    public void removeVehicule(Long id) {
        if (id == null || !vehiculeRepository.existsById(id)) {
            throw new RessourceIntrouvableException(
                    "Impossible de supprimer : vehicule introuvable (id " + id + ").");
        }
        vehiculeRepository.deleteById(id);
        journalisationService.info("Vehicule supprime : id=" + id);
    }

    @Override
    @Transactional
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Agence agence = chargerAgence(idAgence);

        // Transfert : l'ancienne agence ne doit plus referencer le vehicule.
        Agence ancienneAgence = vehicule.getAgence();
        if (ancienneAgence != null && !ancienneAgence.getIdAgence().equals(idAgence)) {
            ancienneAgence.getVehicules().remove(vehicule);
        }

        vehicule.setAgence(agence);
        if (!agence.getVehicules().contains(vehicule)) {
            agence.getVehicules().add(vehicule);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        journalisationService.info("Vehicule affecte : id=" + idVehicule + ", agence id=" + idAgence);
        return saved;
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Equipement equipement = chargerEquipement(idEquipement);

        if (vehicule.getEquipements().contains(equipement)) {
            throw new IllegalArgumentException("L'equipement " + idEquipement
                    + " est deja affecte au vehicule " + idVehicule + ".");
        }

        // Le helper synchronise les deux cotes de la relation @ManyToMany.
        vehicule.addEquipement(equipement);
        Vehicule saved = vehiculeRepository.save(vehicule);
        journalisationService.info("Equipement affecte : equipement id=" + idEquipement
                + ", vehicule id=" + idVehicule);
        return saved;
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Equipement equipement = chargerEquipement(idEquipement);

        if (!vehicule.getEquipements().contains(equipement)) {
            throw new IllegalArgumentException("L'equipement " + idEquipement
                    + " n'est pas affecte au vehicule " + idVehicule + ".");
        }

        vehicule.removeEquipement(equipement);
        Vehicule saved = vehiculeRepository.save(vehicule);
        journalisationService.info("Equipement desaffecte : equipement id=" + idEquipement
                + ", vehicule id=" + idVehicule);
        return saved;
    }

    private Agence chargerAgence(Long idAgence) {
        if (idAgence == null) {
            throw new IllegalArgumentException("L'identifiant de l'agence est obligatoire.");
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new RessourceIntrouvableException(
                        "Agence introuvable avec l'id : " + idAgence));
    }

    private Equipement chargerEquipement(Long idEquipement) {
        if (idEquipement == null) {
            throw new IllegalArgumentException("L'identifiant de l'equipement est obligatoire.");
        }
        return equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new RessourceIntrouvableException(
                        "Equipement introuvable avec l'id : " + idEquipement));
    }
}