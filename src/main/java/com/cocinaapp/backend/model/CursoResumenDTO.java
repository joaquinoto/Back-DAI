package com.cocinaapp.backend.model;

import lombok.Data;

@Data
public class CursoResumenDTO {
    private Integer idCurso;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Double precio;        
    private String modalidad;     
    private Integer duracion; 
}