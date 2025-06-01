package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Receta;
import com.cocinaapp.backend.service.RecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping("/aprobadas")
    public ResponseEntity<List<Receta>> obtenerTodasAprobadas() {
        return ResponseEntity.ok(recetaService.obtenerTodasLasRecetasAprobadas());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Receta>> obtenerPorUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam(required = false, defaultValue = "nombreReceta") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(recetaService.obtenerRecetasPorUsuario(idUsuario, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerPorId(@PathVariable Integer id) {
        return recetaService.obtenerRecetaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearOActualizarReceta(
            @RequestBody Receta receta,
            @RequestParam int idUsuario,
            @RequestParam(defaultValue = "false") boolean reemplazar) {
        try {
            recetaService.crearOActualizarReceta(receta, idUsuario, reemplazar);
            return ResponseEntity.ok("Receta cargada correctamente. Queda pendiente de aprobación.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReceta(@PathVariable Integer id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.ok("Receta eliminada.");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receta>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(required = false, defaultValue = "nombreReceta") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(recetaService.buscarPorNombre(nombre, sortBy, order));
    }

    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<Receta>> buscarPorTipo(
            @PathVariable Integer idTipo,
            @RequestParam(required = false, defaultValue = "nombreReceta") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(recetaService.buscarPorTipo(idTipo, sortBy, order));
    }

    @GetMapping("/ingrediente")
    public ResponseEntity<List<Receta>> buscarPorIngrediente(
            @RequestParam String nombreIngrediente,
            @RequestParam(required = false, defaultValue = "nombreReceta") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(recetaService.buscarPorIngrediente(nombreIngrediente, sortBy, order));
    }

    @GetMapping("/sin-ingrediente")
    public ResponseEntity<List<Receta>> buscarSinIngrediente(
            @RequestParam String nombreIngrediente,
            @RequestParam(required = false, defaultValue = "nombreReceta") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(recetaService.buscarSinIngrediente(nombreIngrediente, sortBy, order));
    }

    
    @PutMapping("/aprobar/{idReceta}")
    public ResponseEntity<?> aprobarReceta(@PathVariable Integer idReceta) {
        recetaService.aprobarReceta(idReceta);
        return ResponseEntity.ok("Receta aprobada y publicada.");
    }

    @GetMapping("/{idReceta}/escalar-visual")
    public ResponseEntity<Receta> escalarRecetaVisual(
            @PathVariable int idReceta,
            @RequestParam int porciones) {
        Receta recetaEscalada = recetaService.escalarRecetaVisual(idReceta, porciones);
        return ResponseEntity.ok(recetaEscalada);
    }

    @GetMapping("/ultimas")
    public ResponseEntity<List<Receta>> obtenerUltimasTres() {
        return ResponseEntity.ok(recetaService.obtenerUltimasTresRecetas());
    }
}