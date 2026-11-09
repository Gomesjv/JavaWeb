package com.petcare.web.repository;

import com.petcare.web.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Override
    @EntityGraph(attributePaths = {"animal", "veterinario"})
    List<Consulta> findAll();

    List<Consulta> findByAnimalId(Long animalId);
    List<Consulta> findByVeterinarioId(Long veterinarioId);
}
