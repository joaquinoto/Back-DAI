package com.cocinaapp.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.CronogramaCurso;

@Repository
public interface CronogramaCursoRepository extends JpaRepository<CronogramaCurso, Integer> {
    @EntityGraph(attributePaths = {"curso", "sede"})
    Optional<CronogramaCurso> findById(Integer id);
    
    @EntityGraph(attributePaths = {"curso", "sede"})
    List<CronogramaCurso> findByCurso_IdCurso(Integer cursoId);
}
