package com.cocinaapp.repository;

import com.cocinaapp.model.CronogramaCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronogramaCursoRepository extends JpaRepository<CronogramaCurso, Integer> {
}
