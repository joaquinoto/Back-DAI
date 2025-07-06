package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Curso;
import com.cocinaapp.backend.model.CursoResumenDTO;
import com.cocinaapp.backend.model.CursoDTO;
import com.cocinaapp.backend.service.CursoService;
import com.cocinaapp.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> listarCursos(@RequestParam(required = false) Integer idUsuario) {
        // ✅ CAMBIO: Usar siempre obtenerTodosLosCursos() en lugar de obtenerCursosDisponiblesParaAlumno()
        List<Curso> cursos = cursoService.obtenerTodosLosCursos();

        if (idUsuario != null && usuarioService.esAlumno(idUsuario)) {
            // Para alumnos, devolver cursos completos con imagen Base64
            List<CursoDTO> cursosCompletos = cursos.stream()
                    .map(CursoDTO::fromCurso)
                    .toList();
            return ResponseEntity.ok(cursosCompletos);
        } else {
            // Para visitantes, devolver solo resumen con URL de imagen
            List<CursoResumenDTO> resumen = cursos.stream().map(curso -> {
                CursoResumenDTO dto = new CursoResumenDTO();
                dto.setIdCurso(curso.getIdCurso());
                dto.setNombre(curso.getNombre());
                dto.setDescripcion(curso.getDescripcion());
                dto.setPrecio(curso.getPrecio());
                dto.setModalidad(curso.getModalidad());
                dto.setDuracion(curso.getDuracion());
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

    @PutMapping("/{id}/imagen")
    public ResponseEntity<Curso> actualizarImagenCurso(
            @PathVariable Integer id,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            Optional<Curso> cursoOpt = cursoService.obtenerCursoPorId(id);
            if (cursoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Curso curso = cursoOpt.get();
            if (imagen != null && !imagen.isEmpty()) {
                curso.setImagen(imagen.getBytes());
            }
            
            Curso cursoActualizado = cursoService.guardarCurso(curso);
            return ResponseEntity.ok(cursoActualizado);
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
        Optional<Curso> cursoOpt = cursoService.obtenerCursoPorId(id);
        
        if (cursoOpt.isPresent() && cursoOpt.get().getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(cursoOpt.get().getImagen(), headers, HttpStatus.OK);
        }
        
        return ResponseEntity.notFound().build();
    }
}