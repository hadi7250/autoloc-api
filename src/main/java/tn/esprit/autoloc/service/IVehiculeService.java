package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import java.util.List;

public interface IVehiculeService {
    List<Vehicule> retrieveAllVehicules();
    Vehicule retrieveVehicule(Long id);
    Vehicule addVehicule(Vehicule v);
    Vehicule modifyVehicule(Vehicule v);
    void removeVehicule(Long id);

    /**
     * Rattache le vehicule a une agence (vehicule neuf ou transfert).
     */
    Vehicule affecterVehicule(Long idVehicule, Long idAgence);

    /**
     * Installe un equipement sur le vehicule.
     */
    Vehicule affecterEquipement(Long idVehicule, Long idEquipement);

    /**
     * Retire un equipement du vehicule.
     */
    Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement);
}