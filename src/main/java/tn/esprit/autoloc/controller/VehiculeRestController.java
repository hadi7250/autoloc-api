package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @GetMapping("/retrieve-vehicule/{id}")
    public Vehicule retrieveVehicule(@PathVariable("id") Long id) {
        return vehiculeService.retrieveVehicule(id);
    }

    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule v) {
        return vehiculeService.addVehicule(v);
    }

    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule v) {
        return vehiculeService.modifyVehicule(v);
    }

    @DeleteMapping("/remove-vehicule/{id}")
    public void removeVehicule(@PathVariable("id") Long id) {
        vehiculeService.removeVehicule(id);
    }
}