package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "calificaciones")
@Data
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCalificacion")
    private Integer idCalificacion;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idReceta", nullable = false)
    private Receta receta;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;  // Si es un valor numérico, este campo es adecuado.

    @Column(name = "comentarios", length = 500)
    private String comentarios;
}
