package com.cocinaapp.controller;

import com.cocinaapp.model.Calificacion;
import com.cocinaapp.model.Receta;
import com.cocinaapp.service.CalificacionService;
import com.cocinaapp.service.RecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public List<Receta> obtenerTodasLasRecetas() {
        return recetaService.obtenerTodasLasRecetas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Integer id) {
        Optional<Receta> receta = recetaService.obtenerRecetaPorId(id);
        return receta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receta> guardarReceta(@RequestBody Receta receta) {
        Receta recetaGuardada = recetaService.guardarReceta(receta);
        return ResponseEntity.status(HttpStatus.CREATED).body(recetaGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Integer id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public List<Receta> buscarPorNombre(@RequestParam String nombre) {
        return recetaService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/tipo/{idTipo}")
    public List<Receta> buscarPorTipo(@PathVariable Integer idTipo) {
        return recetaService.buscarPorTipo(idTipo);
    }

    @GetMapping("/buscar/usuario/{idUsuario}")
    public List<Receta> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return recetaService.buscarPorUsuario(idUsuario);
    }

    @GetMapping("/buscar/ingrediente")
    public List<Receta> buscarPorIngrediente(@RequestParam String ingrediente) {
        return recetaService.buscarPorIngrediente(ingrediente);
    }

    @GetMapping("/buscar/sin-ingrediente")
    public List<Receta> buscarSinIngrediente(@RequestParam String ingrediente) {
        return recetaService.buscarSinIngrediente(ingrediente);
    }

    @PostMapping("/{idReceta}/calificacion")
    public ResponseEntity<Calificacion> calificarReceta(@PathVariable Integer idReceta, @RequestBody Calificacion calificacion) {
        calificacion.setReceta(new Receta());
        calificacion.getReceta().setIdReceta(idReceta);
        Calificacion calificacionGuardada = calificacionService.guardarCalificacion(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionGuardada);
    }
}
