package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_asistencias")
@Data
public class RegistroAsistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_asistencia_curso", nullable = false)
    private AsistenciaCurso asistenciaCurso;

    @Column(nullable = false)
    private LocalDateTime fechaHora;
}