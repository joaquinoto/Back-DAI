package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Calificacion;
import com.cocinaapp.backend.repository.CalificacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    public List<Calificacion> listarAprobadasPorReceta(int idReceta) {
        return calificacionRepository.findByReceta_IdRecetaAndAprobadoTrue(idReceta);
    }

    public List<Calificacion> listarPendientes() {
        return calificacionRepository.findByAprobadoFalse();
    }

    public void aprobarCalificacion(int idCalificacion) {
        Calificacion calificacion = calificacionRepository.findById(idCalificacion)
            .orElseThrow(() -> new IllegalArgumentException("Calificación no encontrada"));
        calificacion.setAprobado(true);
        calificacionRepository.save(calificacion);
    }
}