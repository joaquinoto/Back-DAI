package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
