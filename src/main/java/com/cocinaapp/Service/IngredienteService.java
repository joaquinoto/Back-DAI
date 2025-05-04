package com.cocinaapp.Service;

import com.cocinaapp.model.Ingrediente;
import com.cocinaapp.Repository.IngredienteRepository;
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

    public Optional<Ingrediente> obtenerIngredientePorId(Long id) {
        return ingredienteRepository.findById(id);
    }

    public Ingrediente guardarIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void eliminarIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }
}
