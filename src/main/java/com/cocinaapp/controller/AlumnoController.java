package com.cocinaapp.controller;

import com.cocinaapp.model.Alumno;
import com.cocinaapp.service.AlumnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PreAuthorize("hasRole('ALUMNO')")
    @GetMapping
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoService.obtenerTodosLosAlumnos();
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumnoPorId(@PathVariable Integer id) {
        Optional<Alumno> alumno = alumnoService.obtenerAlumnoPorId(id);
        return alumno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping
    public ResponseEntity<Alumno> guardarAlumno(@RequestBody Alumno alumno) {
        Alumno alumnoGuardado = alumnoService.guardarAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoGuardado);
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Integer id) {
        boolean eliminado = alumnoService.eliminarAlumno(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}