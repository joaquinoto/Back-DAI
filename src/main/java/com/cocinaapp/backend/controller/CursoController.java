package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Curso;
import com.cocinaapp.backend.model.CursoResumenDTO;
import com.cocinaapp.backend.model.CursoDTO;
import com.cocinaapp.backend.service.CursoService;
import com.cocinaapp.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
            // Para alumnos, devolver cursos completos con URL de imagen
            List<CursoDTO> cursosCompletos = cursos.stream()
                    .map(CursoDTO::fromCurso)
                    .toList();
            return ResponseEntity.ok(cursosCompletos);
        } else {
            // Para visitantes, devolver solo resumen
            List<CursoResumenDTO> resumen = cursos.stream().map(curso -> {
                CursoResumenDTO dto = new CursoResumenDTO();
                dto.setIdCurso(curso.getIdCurso());
                dto.setNombre(curso.getNombre());
                dto.setDescripcion(curso.getDescripcion());
                // Generar URL de imagen si existe
                if (curso.getImagen() != null) {
                    dto.setImagenUrl("/api/cursos/" + curso.getIdCurso() + "/imagen");
                }
                return dto;
            }).toList();
            return ResponseEntity.ok(resumen);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerCursoPorId(@PathVariable Integer id) {
        Optional<Curso> curso = cursoService.obtenerCursoPorId(id);
        return curso.map(c -> ResponseEntity.ok(CursoDTO.fromCurso(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> guardarCurso(@RequestBody Curso curso) {
        Curso cursoGuardado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoGuardado);
    }

    @PostMapping("/con-imagen")
    public ResponseEntity<Curso> guardarCursoConImagen(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("contenidos") String contenidos,
            @RequestParam("requerimientos") String requerimientos,
            @RequestParam("duracion") Integer duracion,
            @RequestParam("precio") Double precio,
            @RequestParam("modalidad") String modalidad,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        try {
            Curso curso = new Curso();
            curso.setNombre(nombre);
            curso.setDescripcion(descripcion);
            curso.setContenidos(contenidos);
            curso.setRequerimientos(requerimientos);
            curso.setDuracion(duracion);
            curso.setPrecio(precio);
            curso.setModalidad(modalidad);
            
            if (imagen != null && !imagen.isEmpty()) {
                curso.setImagen(imagen.getBytes());
            }
            
            Curso cursoGuardado = cursoService.guardarCurso(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Integer id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagenCurso(@PathVariable Integer id) {
        Optional<Curso> curso = cursoService.obtenerCursoPorId(id);
        return curso
                .filter(c -> c.getImagen() != null)
                .map(c -> ResponseEntity.ok()
                        .header("Content-Type", "image/jpeg")
                        .body(c.getImagen()))
                .orElse(ResponseEntity.notFound().build());
    }
}
