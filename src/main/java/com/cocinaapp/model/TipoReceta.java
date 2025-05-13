package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tiposReceta")
@Data
public class TipoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipo")
    private Integer idTipo;

    @Column(name = "descripcion", length = 250)
    private String descripcion;
}
