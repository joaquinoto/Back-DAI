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


}
