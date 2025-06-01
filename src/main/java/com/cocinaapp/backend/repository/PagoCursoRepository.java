package com.cocinaapp.backend.repository;

import com.cocinaapp.backend.model.PagoCurso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoCursoRepository extends JpaRepository<PagoCurso, Integer> {
    List<PagoCurso> findByAlumno_IdAlumno(Integer idAlumno);
}