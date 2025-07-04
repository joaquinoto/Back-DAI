package com.cocinaapp.backend.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CursoInscriptoDTO {
    private Integer idAsistencia;
    private String nombreCurso;
    private String nombreSede;
    private String direccionSede;
    private String horario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoPagado;
    private String estado; // ACTIVO, FINALIZADO, CANCELADO
    private LocalDateTime fechaInscripcion;
    private LocalDateTime fechaBaja;
    private Boolean aprobado;
    private Boolean asistencia;
    private Integer totalClases;
}
