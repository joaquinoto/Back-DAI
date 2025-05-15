package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Unidad;
import com.cocinaapp.backend.service.UnidadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    @GetMapping
    public List<Unidad> obtenerTodasLasUnidades() {
        return unidadService.obtenerTodasLasUnidades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidad> obtenerUnidadPorId(@PathVariable Integer id) {
        Optional<Unidad> unidad = unidadService.obtenerUnidadPorId(id);
        return unidad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Unidad> guardarUnidad(@RequestBody Unidad unidad) {
        Unidad unidadGuardada = unidadService.guardarUnidad(unidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnidad(@PathVariable Integer id) {
        unidadService.eliminarUnidad(id);
        return ResponseEntity.noContent().build();
    }
}
