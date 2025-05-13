package com.cocinaapp.service;

import com.cocinaapp.model.Receta;
import com.cocinaapp.repository.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> obtenerRecetaPorId(Integer id) {
        return recetaRepository.findById(id);
    }

    public Receta guardarReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public void eliminarReceta(Integer id) {
        recetaRepository.deleteById(id);
    }

    public List<Receta> buscarPorNombre(String nombre) {
        return recetaRepository.findByNombreRecetaContainingIgnoreCase(nombre);
    }

    public List<Receta> buscarPorTipo(Integer idTipo) {
        return recetaRepository.findByTipoReceta_IdTipo(idTipo);
    }

    public List<Receta> buscarPorUsuario(Integer idUsuario) {
        return recetaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Receta> buscarPorIngrediente(String nombreIngrediente) {
        return recetaRepository.findByIngredientes_NombreIgnoreCase(nombreIngrediente);
    }

    public List<Receta> buscarSinIngrediente(String nombreIngrediente) {
        return recetaRepository.findByIngredientes_NombreNotIgnoreCase(nombreIngrediente);
    }
}
