package com.cocinaapp.model;

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
}
