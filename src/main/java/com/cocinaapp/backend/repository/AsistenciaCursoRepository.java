package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocinaapp.backend.model.AsistenciaCurso;

import java.util.List;

public interface AsistenciaCursoRepository extends JpaRepository<AsistenciaCurso, Integer> {
    boolean existsByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(int idAlumno, int idCronograma);
    AsistenciaCurso findByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(int idAlumno, int idCronograma);
    List<AsistenciaCurso> findByAlumno_IdAlumnoAndFechaBajaIsNull(int idAlumno);
}