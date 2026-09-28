package tn.esprit.autoloc.repository;

import tn.esprit.autoloc.domain.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
