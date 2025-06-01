package com.cocinaapp.backend.repository;

import com.cocinaapp.backend.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    Optional<Promocion> findByCurso_IdCursoAndSede_IdSedeAndActivaTrue(Integer idCurso, Integer idSede);
}