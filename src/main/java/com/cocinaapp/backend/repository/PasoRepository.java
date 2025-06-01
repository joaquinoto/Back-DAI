package com.cocinaapp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Multimedia;
import com.cocinaapp.backend.model.Paso;

@Repository
public interface PasoRepository extends JpaRepository<Paso, Integer> {
}
