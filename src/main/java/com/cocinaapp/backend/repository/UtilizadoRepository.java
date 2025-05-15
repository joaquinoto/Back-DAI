package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Utilizado;

@Repository
public interface UtilizadoRepository extends JpaRepository<Utilizado, Integer> {
}
