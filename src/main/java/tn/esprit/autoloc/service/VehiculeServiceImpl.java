package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule retrieveVehicule(Long id) {
        // Instead of returning null, throw an exception
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public Vehicule addVehicule(Vehicule v) {
        return vehiculeRepository.save(v);
    }

    @Override
    public Vehicule modifyVehicule(Vehicule v) {
        if (!vehiculeRepository.existsById(v.getIdVehicule())) {
            throw new RuntimeException("Impossible de modifier : Vehicule introuvable.");
        }
        return vehiculeRepository.save(v);
    }

    @Override
    public void removeVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }
}