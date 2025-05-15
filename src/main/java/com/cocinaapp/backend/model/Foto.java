package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fotos")
@Data
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfoto")
    private Integer idFoto;

    @ManyToOne
    @JoinColumn(name = "idReceta", nullable = false)
    private Receta receta;

    @Column(name = "urlFoto", nullable = false, length = 300)
    private String urlFoto;

    @Column(name = "extension", nullable = false, length = 5)
    private String extension;
}
