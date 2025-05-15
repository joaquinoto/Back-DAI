package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    
}
