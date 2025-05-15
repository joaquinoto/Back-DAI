package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocinaapp.backend.model.CodigoValidacion;

import java.util.List;
import java.util.Optional;

public interface CodigoValidacionRepository extends JpaRepository<CodigoValidacion, Long> {
    Optional<CodigoValidacion> findByEmailAndUsadoFalse(String email);
    Optional<CodigoValidacion> findByCodigoAndUsadoFalse(String codigo);
    boolean existsByEmailAndUsadoFalse(String email);
    boolean existsByAliasAndUsadoFalse(String alias);
    List<CodigoValidacion> findAllByEmailAndUsadoFalse(String email);
}