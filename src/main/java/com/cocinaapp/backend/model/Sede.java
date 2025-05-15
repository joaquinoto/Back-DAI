package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sedes")
@Data
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSede")
    private Integer idSede;

    @Column(name = "nombreSede", nullable = false, length = 150)
    private String nombreSede;

    @Column(name = "direccionSede", nullable = false, length = 250)
    private String direccionSede;

    @Column(name = "telefonoSede", nullable = true, length = 15)
    private String telefonoSede;

    @Column(name = "mailSede", nullable = true, length = 150)
    private String mailSede;

    @Column(name = "whatsApp", nullable = true, length = 15)
    private String whatsApp;

    @Column(name = "tipoBonificacion", nullable = true, length = 20)
    private String tipoBonificacion;

    @Column(name = "bonificacionCursos", nullable = true)
    private Double bonificacionCursos;

    @Column(name = "tipoPromocion", nullable = true, length = 20)
    private String tipoPromocion;

    @Column(name = "promocionCursos", nullable = true)
    private Double promocionCursos;
}
