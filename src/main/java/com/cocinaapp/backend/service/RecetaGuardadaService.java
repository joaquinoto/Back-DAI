package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Receta;
import com.cocinaapp.backend.model.RecetaGuardada;
import com.cocinaapp.backend.model.RecetaGuardadaDTO;
import com.cocinaapp.backend.model.RecetaGuardadaId;
import com.cocinaapp.backend.repository.RecetaGuardadaRepository;
import com.cocinaapp.backend.repository.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaGuardadaService {

    @Autowired
    private RecetaGuardadaRepository recetaGuardadaRepository;
    
    @Autowired
    private RecetaRepository recetaRepository;

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

    // ✅ MÉTODO MEJORADO: Ahora devuelve RecetaGuardadaDTO con recetas completas
    public List<RecetaGuardadaDTO> listarGuardadas(int idUsuario) {
        List<RecetaGuardada> guardadas = recetaGuardadaRepository.findByIdIdUsuario(idUsuario);
        
        return guardadas.stream()
            .map(guardada -> {
                Receta receta = recetaRepository.findById(guardada.getId().getIdReceta())
                    .orElse(null);
                return new RecetaGuardadaDTO(
                    guardada.getId().getIdUsuario(),
                    guardada.getId().getIdReceta(),
                    guardada.getFechaGuardado(),
                    receta
                );
            })
            .filter(dto -> dto.getReceta() != null) // ✅ Filtrar recetas eliminadas
            .collect(Collectors.toList());
    }
}