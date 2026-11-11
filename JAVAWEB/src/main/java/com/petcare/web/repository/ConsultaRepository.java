package com.petcare.web.repository;

import com.petcare.web.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Override
    @EntityGraph(attributePaths = {"animal", "veterinario"})
    List<Consulta> findAll();

    @Override
    @EntityGraph(attributePaths = {"animal", "veterinario"})
    Optional<Consulta> findById(Long id);

    List<Consulta> findByAnimalId(Long animalId);
    List<Consulta> findByVeterinarioId(Long veterinarioId);
    long countByAnimalId(Long animalId);
    long countByVeterinarioId(Long veterinarioId);
}
