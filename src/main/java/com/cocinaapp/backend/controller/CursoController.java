package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Curso;
import com.cocinaapp.backend.model.CursoResumenDTO;
import com.cocinaapp.backend.service.CursoService;
import com.cocinaapp.backend.service.UsuarioService;

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

    @Autowired
    private com.cocinaapp.backend.service.UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> listarCursos(@RequestParam(required = false) Integer idUsuario) {
        List<Curso> cursos = cursoService.obtenerTodosLosCursos();

        if (idUsuario != null && usuarioService.esAlumno(idUsuario)) {
            return ResponseEntity.ok(cursos);
        } else {
            List<CursoResumenDTO> resumen = cursos.stream().map(curso -> {
                CursoResumenDTO dto = new CursoResumenDTO();
                dto.setIdCurso(curso.getIdCurso());
                dto.setNombre(curso.getNombre());
                dto.setDescripcion(curso.getDescripcion());
                return dto;
            }).toList();
            return ResponseEntity.ok(resumen);
        }
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
