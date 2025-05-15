package com.cocinaapp.backend.model;

import lombok.Data;

@Data
public class RegistroRequest {
    private Usuario usuario;
    private Alumno alumno; // Puede ser null si esAlumno es false
}