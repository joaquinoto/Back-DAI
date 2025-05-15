package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Paso;
import com.cocinaapp.backend.repository.PasoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasoService {

    @Autowired
    private PasoRepository pasoRepository;

    public List<Paso> obtenerTodosLosPasos() {
        return pasoRepository.findAll();
    }

    public Optional<Paso> obtenerPasoPorId(Integer id) {
        return pasoRepository.findById(id);
    }

    public Paso guardarPaso(Paso paso) {
        return pasoRepository.save(paso);
    }

    public void eliminarPaso(Integer id) {
        pasoRepository.deleteById(id);
    }
}
