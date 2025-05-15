package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.CronogramaCurso;
import com.cocinaapp.backend.repository.CronogramaCursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CronogramaCursoService {

    @Autowired
    private CronogramaCursoRepository cronogramaCursoRepository;

    public List<CronogramaCurso> obtenerTodosLosCronogramas() {
        return cronogramaCursoRepository.findAll();
    }

    public Optional<CronogramaCurso> obtenerCronogramaPorId(Integer id) {
        return cronogramaCursoRepository.findById(id);
    }

    public CronogramaCurso guardarCronograma(CronogramaCurso cronogramaCurso) {
        return cronogramaCursoRepository.save(cronogramaCurso);
    }

    public void eliminarCronograma(Integer id) {
        cronogramaCursoRepository.deleteById(id);
    }
}
