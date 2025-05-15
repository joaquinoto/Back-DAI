package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alumnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {

    @Id
    @Column(name = "idAlumno")
    private int idAlumno;

    @Column(name = "numeroTarjeta", length = 12)
    private String numeroTarjeta;

    @Column(name = "dniFrente", length = 300)
    private String dniFrente;

    @Column(name = "dniFondo", length = 300)
    private String dniFondo;

    @Column(name = "tramite", length = 12)
    private String tramite;

    @Column(name = "cuentaCorriente")
    private double cuentaCorriente;

    @OneToOne
    @JoinColumn(name = "idAlumno")
    private Usuario usuario;
}
