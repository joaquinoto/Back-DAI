package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Sede;
import com.cocinaapp.backend.repository.SedeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    public List<Sede> obtenerTodasLasSedes() {
        return sedeRepository.findAll();
    }

    public Optional<Sede> obtenerSedePorId(Integer id) {
        return sedeRepository.findById(id);
    }

    public Sede guardarSede(Sede sede) {
        return sedeRepository.save(sede);
    }

    public void eliminarSede(Integer id) {
        sedeRepository.deleteById(id);
    }
}
