package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.CronogramaCurso;

@Repository
public interface CronogramaCursoRepository extends JpaRepository<CronogramaCurso, Integer> {
}
