package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocinaapp.backend.model.RecetaGuardada;
import com.cocinaapp.backend.model.RecetaGuardadaId;

import java.util.List;

public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {
    List<RecetaGuardada> findByIdIdUsuario(int idUsuario);
    boolean existsById(RecetaGuardadaId id);
    void deleteById(RecetaGuardadaId id);
}