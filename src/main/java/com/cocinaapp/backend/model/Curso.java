package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    private Integer idCurso;

    @Column(name = "descripcion", nullable = true, length = 300)
    private String descripcion;

    @Column(name = "contenidos", nullable = true, length = 500)
    private String contenidos;

    @Column(name = "requerimientos", nullable = true, length = 500)
    private String requerimientos;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "modalidad", nullable = false, length = 20)
    private String modalidad;
}
