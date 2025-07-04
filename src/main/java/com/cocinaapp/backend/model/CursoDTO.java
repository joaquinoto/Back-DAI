package com.cocinaapp.backend.model;

import lombok.Data;

@Data
public class CursoDTO {
    private Integer idCurso;
    private String nombre;
    private String imagenUrl;
    private String imagen; // Base64 de la imagen
    private String descripcion;
    private String contenidos;
    private String requerimientos;
    private Integer duracion;
    private Double precio;
    private String modalidad;
    
    public static CursoDTO fromCurso(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setContenidos(curso.getContenidos());
        dto.setRequerimientos(curso.getRequerimientos());
        dto.setDuracion(curso.getDuracion());
        dto.setPrecio(curso.getPrecio());
        dto.setModalidad(curso.getModalidad());
        
        // Generar URL de la imagen si existe
        if (curso.getImagen() != null) {
            // Convertir bytes a Base64
            dto.setImagen(java.util.Base64.getEncoder().encodeToString(curso.getImagen()));
            dto.setImagenUrl("/api/cursos/" + curso.getIdCurso() + "/imagen");
        }
        
        return dto;
    }
}
