package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "promociones")
@Data
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPromocion")
    private Integer idPromocion;

    @ManyToOne
    @JoinColumn(name = "idSede", nullable = false)
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

    @Column(name = "porcentajeDescuento", nullable = false)
    private double porcentajeDescuento; // Ej: 20.0 para 20%

    @Column(name = "activa", nullable = false)
    private boolean activa;
}