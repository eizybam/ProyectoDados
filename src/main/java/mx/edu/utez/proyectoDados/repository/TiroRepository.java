package mx.edu.utez.proyectoDados.repository;

import mx.edu.utez.proyectoDados.model.tiro.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {
}
