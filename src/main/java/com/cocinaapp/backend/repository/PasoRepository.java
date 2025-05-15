package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Paso;

@Repository
public interface PasoRepository extends JpaRepository<Paso, Integer> {
}
