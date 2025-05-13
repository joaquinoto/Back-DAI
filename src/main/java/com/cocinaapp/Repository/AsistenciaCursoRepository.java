package com.cocinaapp.repository;

import com.cocinaapp.model.AsistenciaCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaCursoRepository extends JpaRepository<AsistenciaCurso, Integer> {
}
