package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Ingrediente;
import com.cocinaapp.backend.model.Receta;
import com.cocinaapp.backend.repository.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> obtenerTodasLasRecetasAprobadas() {
        return recetaRepository.findByAprobadoTrue();
    }

    public List<Receta> obtenerRecetasPorUsuario(Integer idUsuario) {
        return recetaRepository.findByUsuario_IdUsuario(idUsuario);
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
        return recetaRepository.findByNombreRecetaContainingIgnoreCaseAndAprobadoTrue(nombre);
    }

    public List<Receta> buscarPorTipo(Integer idTipo) {
        return recetaRepository.findByTipoReceta_IdTipoAndAprobadoTrue(idTipo);
    }

    public List<Receta> buscarPorUsuario(Integer idUsuario) {
        return recetaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Receta> buscarPorIngrediente(String nombreIngrediente) {
        return recetaRepository.findByIngredientes_NombreIgnoreCaseAndAprobadoTrue(nombreIngrediente);
    }

    public List<Receta> buscarSinIngrediente(String nombreIngrediente) {
        return recetaRepository.findByIngredientes_NombreNotIgnoreCaseAndAprobadoTrue(nombreIngrediente);
    }

   
    public void crearOActualizarReceta(Receta receta, int idUsuario, boolean reemplazar) {
        Optional<Receta> existente = recetaRepository.findByNombreRecetaAndUsuario_IdUsuario(receta.getNombreReceta(), idUsuario);

        if (existente.isPresent()) {
            if (reemplazar) {
                receta.setIdReceta(existente.get().getIdReceta()); // Sobrescribe la existente
                receta.setAprobado(false); // Requiere nueva aprobación
                recetaRepository.save(receta);
            } else {
                throw new IllegalArgumentException("Ya tienes una receta con ese nombre. ¿Deseas reemplazarla o editarla?");
            }
        } else {
            
            receta.setAprobado(false); // Siempre requiere aprobación
            recetaRepository.save(receta);
        }
    }

    public void aprobarReceta(Integer idReceta) {
        Receta receta = recetaRepository.findById(idReceta)
                .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada"));
        receta.setAprobado(true);
        recetaRepository.save(receta);
    }

    public Receta escalarReceta(int idReceta, int porcionesDeseadas) {
    Receta receta = recetaRepository.findById(idReceta)
        .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada"));

    int porcionesOriginales = receta.getPorciones();
    if (porcionesOriginales <= 0) throw new IllegalArgumentException("Porciones originales inválidas.");

    double factor = (double) porcionesDeseadas / porcionesOriginales;

    Receta recetaEscalada = new Receta();
    recetaEscalada.setNombreReceta(receta.getNombreReceta());
    recetaEscalada.setPorciones(porcionesDeseadas);
    recetaEscalada.setDescripcionReceta(receta.getDescripcionReceta());
    
    List<Ingrediente> ingredientesEscalados = new ArrayList<>();
    for (Ingrediente ing : receta.getIngredientes()) {
        Ingrediente ingEscalado = new Ingrediente();
        ingEscalado.setNombre(ing.getNombre());
        ingEscalado.setCantidad(ing.getCantidad() * factor);
        ingEscalado.setUnidad(ing.getUnidad());
        ingredientesEscalados.add(ingEscalado);
    }
    recetaEscalada.setIngredientes(ingredientesEscalados);

    return recetaEscalada;
}
}