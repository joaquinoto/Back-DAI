package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Calificacion;
import com.cocinaapp.backend.service.CalificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @GetMapping("/receta/{idReceta}")
    public ResponseEntity<List<Calificacion>> listarAprobadasPorReceta(@PathVariable int idReceta) {
        return ResponseEntity.ok(calificacionService.listarAprobadasPorReceta(idReceta));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Calificacion>> listarPendientes() {
        return ResponseEntity.ok(calificacionService.listarPendientes());
    }

    @PutMapping("/aprobar/{idCalificacion}")
    public ResponseEntity<?> aprobarCalificacion(@PathVariable int idCalificacion) {
        calificacionService.aprobarCalificacion(idCalificacion);
        return ResponseEntity.ok("Calificación aprobada.");
    }
}
