package tn.esprit.autoloc;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootApplication
public class AutolocApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutolocApiApplication.class, args);
    }

    @Bean
    CommandLineRunner demoData(AgenceRepository agenceRepository,
                               EquipementRepository equipementRepository,
                               VehiculeRepository vehiculeRepository) {
        return args -> {
            if (agenceRepository.count() == 0) {
                // 1. Agency first: Agence -> Vehicule has no cascade, so the agency must exist before linking.
                Agence agence = new Agence();
                agence.setNom("AutoLoc Tunis");
                agence.setVille("Tunis");
                agence.setAdresse("12 Avenue Habib Bourguiba");
                agence.setTelephone("+216 71 000 000");
                agenceRepository.save(agence);

                // 2. Equipment catalog
                Equipement gps = new Equipement();
                gps.setLibelle("GPS");
                Equipement siegeBebe = new Equipement();
                siegeBebe.setLibelle("Siège bébé");
                Equipement coffre = new Equipement();
                coffre.setLibelle("Coffre de toit");
                equipementRepository.saveAll(Set.of(gps, siegeBebe, coffre));

                // 3. Vehicles, linked to the agency and equipped
                Vehicule peugeot = buildVehicule("TN-1234-AB", "Peugeot", "208",
                        CategorieVehicule.CITADINE, new BigDecimal("60.00"), StatutVehicule.DISPONIBLE);
                peugeot.setAgence(agence);
                peugeot.addEquipement(gps);

                Vehicule passat = buildVehicule("TN-5678-CD", "Volkswagen", "Passat",
                        CategorieVehicule.BERLINE, new BigDecimal("110.00"), StatutVehicule.DISPONIBLE);
                passat.setAgence(agence);
                passat.addEquipement(gps);
                passat.addEquipement(siegeBebe);

                Vehicule rav4 = buildVehicule("TN-9012-EF", "Toyota", "RAV4",
                        CategorieVehicule.SUV, new BigDecimal("150.00"), StatutVehicule.MAINTENANCE);
                rav4.setAgence(agence);

                vehiculeRepository.saveAll(Set.of(peugeot, passat, rav4));
            }
        };
    }

    private static Vehicule buildVehicule(String immatriculation, String marque, String modele,
                                          CategorieVehicule categorie, BigDecimal tarifJournalier,
                                          StatutVehicule statut) {
        Vehicule v = new Vehicule();
        v.setImmatriculation(immatriculation);
        v.setMarque(marque);
        v.setModele(modele);
        v.setCategorie(categorie);
        v.setTarifJournalier(tarifJournalier);
        v.setStatut(statut);
        return v;
    }
}
