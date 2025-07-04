package com.cocinaapp.backend.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CuentaCorrienteDTO {
    private Integer id;
    private String tipoMovimiento;
    private BigDecimal monto;
    private String descripcion;
    private LocalDateTime fechaMovimiento;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoActual;
    private Integer idAsistencia;
    private String nombreCurso;
    private String nombreSede;
}
