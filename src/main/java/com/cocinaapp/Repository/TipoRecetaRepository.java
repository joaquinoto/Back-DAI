package com.cocinaapp.repository;

import com.cocinaapp.model.TipoReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRecetaRepository extends JpaRepository<TipoReceta, Integer> {
}
