package com.cocinaapp.backend.model;

import lombok.Data;


@Data
public class LoginRequest {
    private String identificador;     
    private String contrasena;
}