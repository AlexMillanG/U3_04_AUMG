package mx.edu.utez.AplicacionDePrincipios.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CedeRepository extends JpaRepository<CedeEntity,Long> {
}
