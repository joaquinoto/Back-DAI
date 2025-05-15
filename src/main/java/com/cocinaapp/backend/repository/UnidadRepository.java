package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Unidad;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Integer> {
}
