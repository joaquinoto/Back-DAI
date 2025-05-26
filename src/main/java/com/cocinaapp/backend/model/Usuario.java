package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "mail", unique = true, length = 150)
    private String mail;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "habilitado", length = 2)
    private String habilitado;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "avatar", length = 300)
    private String avatar;

    @Column(name= "contrasena", nullable = false, length = 300)
    private String contrasena;

    @Column(name= "rol", nullable = false)
    private String rol; // USUARIO, ALUMNO, ADMIN
}