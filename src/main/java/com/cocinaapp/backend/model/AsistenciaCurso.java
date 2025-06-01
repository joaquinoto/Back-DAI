package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistenciaCursos")
@Data
public class AsistenciaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsistencia")
    private Integer idAsistencia;

    @ManyToOne
    @JoinColumn(name = "idAlumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "idCronograma", nullable = false)
    private CronogramaCurso cronogramaCurso;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "fechaBaja")
    private LocalDateTime fechaBaja;

    @Column(name = "aprobado")
private Boolean aprobado = false;
    @Column(name = "asistencia")
    private Boolean asistencia = false;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fechaCreacion", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaModificacion")
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime fechaModificacion;

}
