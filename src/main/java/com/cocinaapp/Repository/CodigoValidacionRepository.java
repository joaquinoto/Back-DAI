package com.cocinaapp.repository;

import com.cocinaapp.model.CodigoValidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoValidacionRepository extends JpaRepository<CodigoValidacion, Long> {
    Optional<CodigoValidacion> findByEmail(String email);
}