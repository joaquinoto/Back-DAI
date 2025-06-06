package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.RecetaGuardada;
import com.cocinaapp.backend.model.RecetaGuardadaId;
import com.cocinaapp.backend.repository.RecetaGuardadaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaGuardadaService {

    @Autowired
    private RecetaGuardadaRepository recetaGuardadaRepository;

    public void guardarReceta(int idUsuario, int idReceta) {
        RecetaGuardadaId id = new RecetaGuardadaId();
        id.setIdUsuario(idUsuario);
        id.setIdReceta(idReceta);

        if (!recetaGuardadaRepository.existsById(id)) {
            RecetaGuardada guardada = new RecetaGuardada();
            guardada.setId(id);
            recetaGuardadaRepository.save(guardada);
        }
    }

    public void eliminarGuardada(int idUsuario, int idReceta) {
        RecetaGuardadaId id = new RecetaGuardadaId();
        id.setIdUsuario(idUsuario);
        id.setIdReceta(idReceta);
        recetaGuardadaRepository.deleteById(id);
    }

    public List<RecetaGuardada> listarGuardadas(int idUsuario) {
        return recetaGuardadaRepository.findByIdIdUsuario(idUsuario);
    }
}