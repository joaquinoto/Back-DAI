package com.cocinaapp.controller;

import com.cocinaapp.model.Alumno;
import com.cocinaapp.service.AlumnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoService.obtenerTodosLosAlumnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumnoPorId(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoService.obtenerAlumnoPorId(id);
        return alumno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alumno> guardarAlumno(@RequestBody Alumno alumno) {
        Alumno alumnoGuardado = alumnoService.guardarAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        boolean eliminado = alumnoService.eliminarAlumno(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
