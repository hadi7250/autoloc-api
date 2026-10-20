package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Gestion des véhicules", description = "API CRUD pour les véhicules")
@RestController
@RequiredArgsConstructor // Better than @AllArgsConstructor for final fields (DIP)
@RequestMapping("/vehicule")
public class VehiculeRestController {

    // DIP: Depends on the abstraction (Interface), not the implementation
    private final IVehiculeService vehiculeService;

    @Operation(description = "Récupérer tous les véhicules de la base")
    @GetMapping("/retrieve-all-vehicules")
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        return ResponseEntity.ok(vehiculeService.retrieveAllVehicules());
    }

    @Operation(description = "Récupérer un véhicule par son identifiant")
    @GetMapping("/retrieve-vehicule/{id}")
    public ResponseEntity<Vehicule> retrieveVehicule(@PathVariable("id") Long id) {
        // The GlobalExceptionHandler will catch the exception if ID doesn't exist
        return ResponseEntity.ok(vehiculeService.retrieveVehicule(id));
    }

    @Operation(description = "Ajouter un nouveau véhicule")
    @PostMapping("/add-vehicule")
    public ResponseEntity<Vehicule> addVehicule(@RequestBody Vehicule v) {
        // SRP: Controller handles HTTP 201 Created, not just the data
        Vehicule created = vehiculeService.addVehicule(v);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(description = "Modifier un véhicule existant")
    @PutMapping("/modify-vehicule")
    public ResponseEntity<Vehicule> modifyVehicule(@RequestBody Vehicule v) {
        return ResponseEntity.ok(vehiculeService.modifyVehicule(v));
    }

    @Operation(description = "Supprimer un véhicule par son identifiant")
    @DeleteMapping("/remove-vehicule/{id}")
    public ResponseEntity<Void> removeVehicule(@PathVariable("id") Long id) {
        vehiculeService.removeVehicule(id);
        // SRP: Return 204 No Content for a successful delete
        return ResponseEntity.noContent().build();
    }
}