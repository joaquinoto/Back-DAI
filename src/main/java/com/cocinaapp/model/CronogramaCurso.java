package com.cocinaapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "cronogramaCursos")
@Data
public class CronogramaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCronograma")
    private Integer idCronograma;

    @ManyToOne
    @JoinColumn(name = "idSede", nullable = false)
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    @Column(name = "vacantesDisponibles")
    private Integer vacantesDisponibles;
}
