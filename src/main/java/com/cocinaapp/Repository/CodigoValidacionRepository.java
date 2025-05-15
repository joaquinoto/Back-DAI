package com.cocinaapp.repository;

import com.cocinaapp.model.CodigoValidacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoValidacionRepository extends JpaRepository<CodigoValidacion, Long> {
    Optional<CodigoValidacion> findByMailAndUsadoFalse(String mail);
    Optional<CodigoValidacion> findByCodigoAndUsadoFalse(String codigo);
    boolean existsByMailAndUsadoFalse(String mail);
    boolean existsByAliasAndUsadoFalse(String alias);
}