package com.cocinaapp.controller;

import com.cocinaapp.model.Sede;
import com.cocinaapp.service.SedeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping
    public List<Sede> obtenerTodasLasSedes() {
        return sedeService.obtenerTodasLasSedes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerSedePorId(@PathVariable Integer id) {
        Optional<Sede> sede = sedeService.obtenerSedePorId(id);
        return sede.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sede> guardarSede(@RequestBody Sede sede) {
        Sede sedeGuardada = sedeService.guardarSede(sede);
        return ResponseEntity.status(HttpStatus.CREATED).body(sedeGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSede(@PathVariable Integer id) {
        sedeService.eliminarSede(id);
        return ResponseEntity.noContent().build();
    }
}
