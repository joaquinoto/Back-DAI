package com.cocinaapp.repository;

import com.cocinaapp.model.RecetaGuardada;
import com.cocinaapp.model.RecetaGuardadaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {
    List<RecetaGuardada> findByIdIdUsuario(int idUsuario);
    boolean existsById(RecetaGuardadaId id);
    void deleteById(RecetaGuardadaId id);
}