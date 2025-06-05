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

    @Column(name = "dniFrente", columnDefinition = "LONGBLOB")
    private byte[] dniFrente;

    @Column(name = "dniFondo", columnDefinition = "LONGBLOB")
    private byte[] dniFondo;

    @Column(name = "tramite", length = 12)
    private String tramite;

    @Column(name = "cuentaCorriente")
    private double cuentaCorriente;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idAlumno")
    private Usuario usuario;
}
