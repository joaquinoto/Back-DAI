package com.cocinaapp.service;

import com.cocinaapp.model.Unidad;
import com.cocinaapp.repository.UnidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadService {

    @Autowired
    private UnidadRepository unidadRepository;

    public List<Unidad> obtenerTodasLasUnidades() {
        return unidadRepository.findAll();
    }

    public Optional<Unidad> obtenerUnidadPorId(Integer id) {
        return unidadRepository.findById(id);
    }

    public Unidad guardarUnidad(Unidad unidad) {
        return unidadRepository.save(unidad);
    }

    public void eliminarUnidad(Integer id) {
        unidadRepository.deleteById(id);
    }
}
