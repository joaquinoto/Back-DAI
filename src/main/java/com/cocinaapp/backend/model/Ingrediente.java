package com.cocinaapp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ingredientes")
@Data
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIngrediente")
    private Integer idIngrediente;

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "unidad", length = 50)
    private String unidad;

    @ManyToOne
    @JoinColumn(name = "idReceta")
    @JsonBackReference
    private Receta receta;
}