package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuenta_corriente")
@Data
public class CuentaCorriente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idAlumno", nullable = false)
    private Alumno alumno;
    
    @Column(name = "tipoMovimiento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;
    
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "fechaMovimiento", nullable = false)
    private LocalDateTime fechaMovimiento;
    
    @ManyToOne
    @JoinColumn(name = "idAsistencia")
    private AsistenciaCurso asistencia;
    
    @Column(name = "saldoAnterior", precision = 10, scale = 2)
    private BigDecimal saldoAnterior;
    
    @Column(name = "saldoActual", precision = 10, scale = 2)
    private BigDecimal saldoActual;
    
    public enum TipoMovimiento {
        DEBITO_INSCRIPCION,    // Pago de curso
        CREDITO_REINTEGRO,     // Reintegro por baja
        CREDITO_SALDO,         // Saldo a favor
        DEBITO_APLICACION      // Aplicación de saldo a favor
    }
}
