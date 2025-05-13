package com.cocinaapp.controller;

import com.cocinaapp.model.Curso;
import com.cocinaapp.service.CursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> obtenerTodosLosCursos() {
        return cursoService.obtenerTodosLosCursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable Integer id) {
        Optional<Curso> curso = cursoService.obtenerCursoPorId(id);
        return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> guardarCurso(@RequestBody Curso curso) {
        Curso cursoGuardado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Integer id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
