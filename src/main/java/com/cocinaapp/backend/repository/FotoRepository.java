package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {
}
