package com.petcare.web.repository;

import com.petcare.web.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @Override
    @EntityGraph(attributePaths = "cliente")
    List<Animal> findAll();

    List<Animal> findByClienteId(Long clienteId);
}
