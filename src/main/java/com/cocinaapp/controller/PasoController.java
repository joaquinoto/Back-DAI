package com.cocinaapp.controller;

import com.cocinaapp.model.Paso;
import com.cocinaapp.service.PasoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pasos")
public class PasoController {

    @Autowired
    private PasoService pasoService;

    @GetMapping
    public List<Paso> obtenerTodosLosPasos() {
        return pasoService.obtenerTodosLosPasos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paso> obtenerPasoPorId(@PathVariable Integer id) {
        Optional<Paso> paso = pasoService.obtenerPasoPorId(id);
        return paso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paso> guardarPaso(@RequestBody Paso paso) {
        Paso pasoGuardado = pasoService.guardarPaso(paso);
        return ResponseEntity.status(HttpStatus.CREATED).body(pasoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaso(@PathVariable Integer id) {
        pasoService.eliminarPaso(id);
        return ResponseEntity.noContent().build();
    }
}
