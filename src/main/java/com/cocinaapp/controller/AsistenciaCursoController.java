package com.cocinaapp.controller;

import com.cocinaapp.model.AsistenciaCurso;
import com.cocinaapp.service.AsistenciaCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaCursoController {

    @Autowired
    private AsistenciaCursoService asistenciaCursoService;

    @GetMapping
    public List<AsistenciaCurso> obtenerTodasLasAsistencias() {
        return asistenciaCursoService.obtenerTodasLasAsistencias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaCurso> obtenerAsistenciaPorId(@PathVariable Integer id) {
        Optional<AsistenciaCurso> asistenciaCurso = asistenciaCursoService.obtenerAsistenciaPorId(id);
        return asistenciaCurso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AsistenciaCurso> guardarAsistencia(@RequestBody AsistenciaCurso asistenciaCurso) {
        AsistenciaCurso asistenciaCursoGuardada = asistenciaCursoService.guardarAsistencia(asistenciaCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaCursoGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistencia(@PathVariable Integer id) {
        asistenciaCursoService.eliminarAsistencia(id);
        return ResponseEntity.noContent().build();
    }
}
