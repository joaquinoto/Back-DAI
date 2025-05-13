package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidades")
@Data
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidad")
    private Integer idUnidad;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;
}
