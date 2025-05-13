package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pasos")
@Data
public class Paso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaso")
    private Integer idPaso;

    @ManyToOne
    @JoinColumn(name = "idReceta", nullable = false)
    private Receta receta;

    @Column(name = "nroPaso", nullable = false)
    private Integer nroPaso;

    @Column(name = "texto", nullable = false, length = 4000)
    private String texto;
}
