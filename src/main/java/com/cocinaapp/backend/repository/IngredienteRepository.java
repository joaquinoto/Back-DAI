package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocinaapp.backend.model.Ingrediente;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByReceta_IdReceta(Integer idReceta);
}
