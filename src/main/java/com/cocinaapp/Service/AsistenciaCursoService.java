package com.cocinaapp.service;

import com.cocinaapp.model.AsistenciaCurso;
import com.cocinaapp.repository.AsistenciaCursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaCursoService {

    @Autowired
    private AsistenciaCursoRepository asistenciaCursoRepository;

    public List<AsistenciaCurso> obtenerTodasLasAsistencias() {
        return asistenciaCursoRepository.findAll();
    }

    public Optional<AsistenciaCurso> obtenerAsistenciaPorId(Integer id) {
        return asistenciaCursoRepository.findById(id);
    }

    public AsistenciaCurso guardarAsistencia(AsistenciaCurso asistenciaCurso) {
        return asistenciaCursoRepository.save(asistenciaCurso);
    }

    public void eliminarAsistencia(Integer id) {
        asistenciaCursoRepository.deleteById(id);
    }
}
