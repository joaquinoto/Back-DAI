package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos_curso")
@Data
public class PagoCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idAlumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "idCronograma", nullable = false)
    private CronogramaCurso cronogramaCurso;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String tipo; // "PAGO" o "REINTEGRO"

    @Column(nullable = false)
    private LocalDateTime fecha;
}