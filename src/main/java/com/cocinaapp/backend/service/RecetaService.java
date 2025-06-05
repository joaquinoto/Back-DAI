package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Ingrediente;
import com.cocinaapp.backend.model.Receta;
import com.cocinaapp.backend.model.Usuario;
import com.cocinaapp.backend.repository.RecetaRepository;
import com.cocinaapp.backend.repository.UsuarioRepository;

import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Receta> obtenerTodasLasRecetasAprobadas() {
        return recetaRepository.findByAprobadoTrue();
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

    public List<Receta> buscarPorNombre(String nombre, String sortBy, String order) {
    Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
    return recetaRepository.findByNombreRecetaContainingIgnoreCaseAndAprobadoTrue(nombre, sort);
    }

    public List<Receta> buscarPorTipo(Integer idTipo, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return recetaRepository.findByTipoReceta_IdTipoAndAprobadoTrue(idTipo, sort);
    }   

    public List<Receta> buscarPorIngrediente(String nombreIngrediente, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return recetaRepository.findByIngredientes_NombreIgnoreCaseAndAprobadoTrue(nombreIngrediente, sort);
    }

    public List<Receta> buscarSinIngrediente(String nombreIngrediente, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return recetaRepository.findByIngredientes_NombreNotContainingIgnoreCaseAndAprobadoTrue(nombreIngrediente, sort);
    }

    public List<Receta> obtenerRecetasPorUsuario(Integer idUsuario, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return recetaRepository.findByUsuario_IdUsuario(idUsuario, sort);
    }

   
    public void crearOActualizarReceta(Receta receta, int idUsuario, boolean reemplazar) {
    Optional<Receta> existente = recetaRepository.findByNombreRecetaAndUsuario_IdUsuario(receta.getNombreReceta(), idUsuario);

    // Asignar el usuario a la receta
    Usuario usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    receta.setUsuario(usuario);

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

public Receta escalarRecetaVisual(int idReceta, int porcionesDeseadas) {
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

    public List<Receta> obtenerUltimasTresRecetas() {
        return recetaRepository.findTop3ByAprobadoTrueOrderByFechaCreacionDesc();
    }
}