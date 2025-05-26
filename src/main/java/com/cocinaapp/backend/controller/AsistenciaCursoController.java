package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.AsistenciaCurso;
import com.cocinaapp.backend.model.RegistroAsistencia;
import com.cocinaapp.backend.service.AsistenciaCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class AsistenciaCursoController {

    @Autowired
    private AsistenciaCursoService asistenciaCursoService;
    

    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribir(@RequestParam int idAlumno, @RequestParam int idCronograma) {
        try {
            asistenciaCursoService.inscribirAlumno(idAlumno, idCronograma);
            return ResponseEntity.ok("Inscripción exitosa.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/baja")
    public ResponseEntity<?> baja(@RequestParam int idAlumno, @RequestParam int idCronograma) {
        try {
            asistenciaCursoService.bajaAlumno(idAlumno, idCronograma);
            return ResponseEntity.ok("Baja exitosa.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/inscriptos")
    public ResponseEntity<List<AsistenciaCurso>> cursosInscripto(@RequestParam int idAlumno) {
        return ResponseEntity.ok(asistenciaCursoService.cursosInscripto(idAlumno));
    }

    @PostMapping("/registrar-asistencia")
    public ResponseEntity<?> registrarAsistencia(@RequestParam int idAlumno, @RequestParam int idCronograma) {
        try {
            asistenciaCursoService.registrarAsistencia(idAlumno, idCronograma);
            return ResponseEntity.ok("Asistencia registrada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/historial-asistencia")
    public ResponseEntity<List<RegistroAsistencia>> historialAsistencia(
            @RequestParam int idAlumno, @RequestParam int idCronograma) {
        AsistenciaCurso asistencia = asistenciaCursoService
            .buscarAsistenciaCurso(idAlumno, idCronograma);
        List<RegistroAsistencia> historial = asistenciaCursoService
            .obtenerHistorialAsistencia(asistencia.getIdAsistencia());
        return ResponseEntity.ok(historial);
}
}