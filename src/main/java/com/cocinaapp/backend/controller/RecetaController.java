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
    public ResponseEntity<List<Receta>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(recetaService.obtenerRecetasPorUsuario(idUsuario));
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
    public ResponseEntity<List<Receta>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(recetaService.buscarPorNombre(nombre));
    }

    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<Receta>> buscarPorTipo(@PathVariable Integer idTipo) {
        return ResponseEntity.ok(recetaService.buscarPorTipo(idTipo));
    }

    @GetMapping("/ingrediente")
    public ResponseEntity<List<Receta>> buscarPorIngrediente(@RequestParam String nombreIngrediente) {
        return ResponseEntity.ok(recetaService.buscarPorIngrediente(nombreIngrediente));
    }

    @GetMapping("/sin-ingrediente")
    public ResponseEntity<List<Receta>> buscarSinIngrediente(@RequestParam String nombreIngrediente) {
        return ResponseEntity.ok(recetaService.buscarSinIngrediente(nombreIngrediente));
    }

    // Endpoint para aprobar receta (solo admin)
    @PutMapping("/aprobar/{idReceta}")
    // @PreAuthorize("hasRole('ADMIN')") // Descomenta si usas seguridad por roles
    public ResponseEntity<?> aprobarReceta(@PathVariable Integer idReceta) {
        recetaService.aprobarReceta(idReceta);
        return ResponseEntity.ok("Receta aprobada y publicada.");
    }

    @GetMapping("/{idReceta}/escalar")
    public ResponseEntity<Receta> escalarReceta(
            @PathVariable int idReceta,
            @RequestParam int porciones) {
        Receta recetaEscalada = recetaService.escalarReceta(idReceta, porciones);
        return ResponseEntity.ok(recetaEscalada);
    }
}