package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.PagoCurso;
import com.cocinaapp.backend.repository.PagoCursoRepository;
import com.cocinaapp.backend.service.AlumnoService;

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

    @Autowired
    private PagoCursoRepository pagoCursoRepository;


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

    @GetMapping("/{id}/cuenta-corriente")
    public ResponseEntity<Double> obtenerCuentaCorriente(@PathVariable Integer id) {
        return alumnoService.obtenerAlumnoPorId(id)
            .map(alumno -> ResponseEntity.ok(alumno.getCuentaCorriente()))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/movimientos-cuenta")
    public ResponseEntity<List<PagoCurso>> obtenerMovimientosCuenta(@PathVariable Integer id) {
        List<PagoCurso> movimientos = pagoCursoRepository.findByAlumno_IdAlumno(id);
        return ResponseEntity.ok(movimientos);
    }
}