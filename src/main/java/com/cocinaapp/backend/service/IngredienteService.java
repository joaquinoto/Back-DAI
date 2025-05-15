package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Ingrediente;
import com.cocinaapp.backend.repository.IngredienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> obtenerTodosLosIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Optional<Ingrediente> obtenerIngredientePorId(Integer id) {
        return ingredienteRepository.findById(id);
    }

    public Ingrediente guardarIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void eliminarIngrediente(Integer id) {
        ingredienteRepository.deleteById(id);
    }
}
