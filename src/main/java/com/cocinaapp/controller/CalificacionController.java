package com.cocinaapp.controller;

import com.cocinaapp.model.Calificacion;
import com.cocinaapp.service.CalificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public List<Calificacion> obtenerTodasLasCalificaciones() {
        return calificacionService.obtenerTodasLasCalificaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> obtenerCalificacionPorId(@PathVariable Integer id) {
        Optional<Calificacion> calificacion = calificacionService.obtenerCalificacionPorId(id);
        return calificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Calificacion> guardarCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion calificacionGuardada = calificacionService.guardarCalificacion(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Integer id) {
        calificacionService.eliminarCalificacion(id);
        return ResponseEntity.noContent().build();
    }
}
