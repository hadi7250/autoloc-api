package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
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
}