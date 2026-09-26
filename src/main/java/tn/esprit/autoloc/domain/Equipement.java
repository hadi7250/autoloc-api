package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="equipement")
@NoArgsConstructor
@AllArgsConstructor

public class Equipement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEquipement;

    @Column(nullable = false, length = 100)
    private String libelle;
}
