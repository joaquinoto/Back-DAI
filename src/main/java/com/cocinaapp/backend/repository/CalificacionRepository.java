package com.cocinaapp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByReceta_IdRecetaAndAprobadoTrue(int idReceta);
    List<Calificacion> findByAprobadoFalse();
}