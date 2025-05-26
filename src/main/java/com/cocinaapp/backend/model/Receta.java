package com.cocinaapp.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recetas")
@Data
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta")
    private Integer idReceta;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

    @Column(name = "nombreReceta", length = 500)
    private String nombreReceta;

    @Column(name = "descripcionReceta", length = 1000)
    private String descripcionReceta;

    @Column(name = "fotoPrincipal", length = 300)
    private String fotoPrincipal;

    @Column(name = "porciones")
    private Integer porciones;

    @Column(name = "cantidadPersonas")
    private Integer cantidadPersonas;

    @ManyToOne
    @JoinColumn(name = "idTipo", referencedColumnName = "idTipo")
    private TipoReceta tipoReceta;

    @Column(name = "aprobado")
    private boolean aprobado = false;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ingrediente> ingredientes = new ArrayList<>();

    @Column(name = "fechaCreacion")
    private java.time.LocalDateTime fechaCreacion = java.time.LocalDateTime.now();
}