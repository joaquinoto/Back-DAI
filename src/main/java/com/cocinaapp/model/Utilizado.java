package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "utilizados")
@Data
public class Utilizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtilizado")
    private Integer idUtilizado;

    @ManyToOne
    @JoinColumn(name = "idReceta", nullable = false)
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "idIngrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idUnidad", nullable = false)
    private Unidad unidad;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}
