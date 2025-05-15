package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
}
