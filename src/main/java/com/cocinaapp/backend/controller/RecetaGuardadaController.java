package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.RecetaGuardada;
import com.cocinaapp.backend.service.RecetaGuardadaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas/guardadas")
public class RecetaGuardadaController {

    @Autowired
    private RecetaGuardadaService recetaGuardadaService;

    @PostMapping("/{idReceta}")
    public ResponseEntity<?> guardarReceta(@RequestParam int idUsuario, @PathVariable int idReceta) {
        recetaGuardadaService.guardarReceta(idUsuario, idReceta);
        return ResponseEntity.ok("Receta guardada.");
    }

    @DeleteMapping("/{idReceta}")
    public ResponseEntity<?> eliminarGuardada(@RequestParam int idUsuario, @PathVariable int idReceta) {
        recetaGuardadaService.eliminarGuardada(idUsuario, idReceta);
        return ResponseEntity.ok("Receta eliminada de guardados.");
    }

    @GetMapping
    public ResponseEntity<List<RecetaGuardada>> listarGuardadas(@RequestParam int idUsuario) {
        return ResponseEntity.ok(recetaGuardadaService.listarGuardadas(idUsuario));
    }
}