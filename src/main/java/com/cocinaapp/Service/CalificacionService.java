package com.cocinaapp.service;

import com.cocinaapp.model.Calificacion;
import com.cocinaapp.repository.CalificacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    public List<Calificacion> obtenerTodasLasCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Optional<Calificacion> obtenerCalificacionPorId(Integer id) {
        return calificacionRepository.findById(id);
    }

    public Calificacion guardarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public void eliminarCalificacion(Integer id) {
        calificacionRepository.deleteById(id);
    }
}
