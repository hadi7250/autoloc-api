package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Gestion des véhicules", description = "API CRUD pour les véhicules")
@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @Operation(description = "Récupérer tous les véhicules de la base")
    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(description = "Récupérer un véhicule par son identifiant")
    @GetMapping("/retrieve-vehicule/{id}")
    public Vehicule retrieveVehicule(@PathVariable("id") Long id) {
        return vehiculeService.retrieveVehicule(id);
    }

    @Operation(description = "Ajouter un nouveau véhicule")
    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule v) {
        return vehiculeService.addVehicule(v);
    }

    @Operation(description = "Modifier un véhicule existant")
    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule v) {
        return vehiculeService.modifyVehicule(v);
    }

    @Operation(description = "Supprimer un véhicule par son identifiant")
    @DeleteMapping("/remove-vehicule/{id}")
    public void removeVehicule(@PathVariable("id") Long id) {
        vehiculeService.removeVehicule(id);
    }
}