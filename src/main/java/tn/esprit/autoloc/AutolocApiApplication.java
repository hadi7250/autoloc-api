package tn.esprit.autoloc;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class AutolocApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutolocApiApplication.class, args);
    }

    @Bean
    CommandLineRunner demoData(VehiculeRepository vehiculeRepository) {
        return args -> {
            if (vehiculeRepository.count() == 0) {
                vehiculeRepository.save(new Vehicule(
                        null, "TN-1234-AB", "Peugeot", "208",
                        CategorieVehicule.CITADINE, new BigDecimal("60.00"),
                        StatutVehicule.DISPONIBLE
                ));
                vehiculeRepository.save(new Vehicule(
                        null, "TN-5678-CD", "Volkswagen", "Passat",
                        CategorieVehicule.BERLINE, new BigDecimal("110.00"),
                        StatutVehicule.DISPONIBLE
                ));
                vehiculeRepository.save(new Vehicule(
                        null, "TN-9012-EF", "Toyota", "RAV4",
                        CategorieVehicule.SUV, new BigDecimal("150.00"),
                        StatutVehicule.MAINTENANCE
                ));
            }
        };
    }
}