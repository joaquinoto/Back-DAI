package com.cocinaapp.controller;

import com.cocinaapp.model.CronogramaCurso;
import com.cocinaapp.service.CronogramaCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cronogramas")
public class CronogramaCursoController {

    @Autowired
    private CronogramaCursoService cronogramaCursoService;

    @GetMapping
    public List<CronogramaCurso> obtenerTodosLosCronogramas() {
        return cronogramaCursoService.obtenerTodosLosCronogramas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CronogramaCurso> obtenerCronogramaPorId(@PathVariable Integer id) {
        Optional<CronogramaCurso> cronogramaCurso = cronogramaCursoService.obtenerCronogramaPorId(id);
        return cronogramaCurso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CronogramaCurso> guardarCronograma(@RequestBody CronogramaCurso cronogramaCurso) {
        CronogramaCurso cronogramaCursoGuardado = cronogramaCursoService.guardarCronograma(cronogramaCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cronogramaCursoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCronograma(@PathVariable Integer id) {
        cronogramaCursoService.eliminarCronograma(id);
        return ResponseEntity.noContent().build();
    }
}
