package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Curso;
import com.cocinaapp.backend.model.Promocion;
import com.cocinaapp.backend.repository.CursoRepository;
import com.cocinaapp.backend.repository.PromocionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private PromocionRepository promocionRepository;

    public List<Curso> obtenerTodosLosCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> obtenerCursoPorId(Integer id) {
        return cursoRepository.findById(id);
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Integer id) {
        cursoRepository.deleteById(id);
    }

}
