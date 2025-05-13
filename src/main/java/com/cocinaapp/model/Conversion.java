package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "conversiones")
@Data
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConversion")
    private Integer idConversion;

    @ManyToOne
    @JoinColumn(name = "idUnidadOrigen", nullable = false)
    private Unidad unidadOrigen;

    @ManyToOne
    @JoinColumn(name = "idUnidadDestino", nullable = false)
    private Unidad unidadDestino;

    @Column(name = "factorConversiones", nullable = false)
    private Float factorConversiones;
}
