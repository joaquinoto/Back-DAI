package com.cocinaapp.repository;

import com.cocinaapp.model.Paso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasoRepository extends JpaRepository<Paso, Integer> {
}
