package com.cocinaapp.backend.model;

import lombok.Data;

@Data
public class RegistroRequest {
    private String email;
    private String codigo;
    private Usuario usuario;
    private Alumno alumno;
    private boolean esAlumno;
}