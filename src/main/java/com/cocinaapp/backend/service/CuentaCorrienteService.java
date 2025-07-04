package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.*;
import com.cocinaapp.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaCorrienteService {
    
    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;
    
    @Autowired
    private AsistenciaCursoRepository asistenciaCursoRepository;
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    public EstadoCuentaDTO obtenerEstadoCuenta(Integer idAlumno) {
        EstadoCuentaDTO estado = new EstadoCuentaDTO();
        
        // Obtener saldo actual
        BigDecimal saldo = obtenerSaldoActual(idAlumno);
        estado.setSaldoActual(saldo);
        
        // Obtener movimientos
        List<CuentaCorriente> movimientos = cuentaCorrienteRepository.findByAlumno_IdAlumnoOrderByFechaMovimientoDesc(idAlumno);
        estado.setMovimientos(movimientos.stream().map(this::convertirADTO).collect(Collectors.toList()));
        
        // Obtener cursos inscriptos
        List<AsistenciaCurso> asistencias = asistenciaCursoRepository.findByAlumno_IdAlumno(idAlumno);
        estado.setCursosInscriptos(asistencias.stream().map(this::convertirACursoDTO).collect(Collectors.toList()));
        
        return estado;
    }
    
    public BigDecimal obtenerSaldoActual(Integer idAlumno) {
        CuentaCorriente ultimoMovimiento = cuentaCorrienteRepository.findTopByAlumno_IdAlumnoOrderByFechaMovimientoDesc(idAlumno);
        return ultimoMovimiento != null ? ultimoMovimiento.getSaldoActual() : BigDecimal.ZERO;
    }
    
    @Transactional
    public void registrarMovimiento(Integer idAlumno, CuentaCorriente.TipoMovimiento tipo, 
                                   BigDecimal monto, String descripcion, Integer idAsistencia) {
        Alumno alumno = alumnoRepository.findById(idAlumno).orElseThrow();
        BigDecimal saldoAnterior = obtenerSaldoActual(idAlumno);
        
        CuentaCorriente movimiento = new CuentaCorriente();
        movimiento.setAlumno(alumno);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setMonto(monto);
        movimiento.setDescripcion(descripcion);
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setSaldoAnterior(saldoAnterior);
        
        if (idAsistencia != null) {
            AsistenciaCurso asistencia = asistenciaCursoRepository.findById(idAsistencia).orElse(null);
            movimiento.setAsistencia(asistencia);
        }
        
        // Calcular nuevo saldo
        BigDecimal nuevoSaldo = saldoAnterior;
        if (tipo == CuentaCorriente.TipoMovimiento.CREDITO_REINTEGRO || 
            tipo == CuentaCorriente.TipoMovimiento.CREDITO_SALDO) {
            nuevoSaldo = saldoAnterior.add(monto);
        } else {
            nuevoSaldo = saldoAnterior.subtract(monto);
        }
        
        movimiento.setSaldoActual(nuevoSaldo);
        cuentaCorrienteRepository.save(movimiento);
    }
    
    private CuentaCorrienteDTO convertirADTO(CuentaCorriente movimiento) {
        CuentaCorrienteDTO dto = new CuentaCorrienteDTO();
        dto.setId(movimiento.getId());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento().toString());
        dto.setMonto(movimiento.getMonto());
        dto.setDescripcion(movimiento.getDescripcion());
        dto.setFechaMovimiento(movimiento.getFechaMovimiento());
        dto.setSaldoAnterior(movimiento.getSaldoAnterior());
        dto.setSaldoActual(movimiento.getSaldoActual());
        
        if (movimiento.getAsistencia() != null) {
            dto.setIdAsistencia(movimiento.getAsistencia().getIdAsistencia());
            dto.setNombreCurso(movimiento.getAsistencia().getCronogramaCurso().getCurso().getNombre());
            dto.setNombreSede(movimiento.getAsistencia().getCronogramaCurso().getSede().getNombreSede());
        }
        
        return dto;
    }
    
    private CursoInscriptoDTO convertirACursoDTO(AsistenciaCurso asistencia) {
        CursoInscriptoDTO dto = new CursoInscriptoDTO();
        dto.setIdAsistencia(asistencia.getIdAsistencia());
        dto.setNombreCurso(asistencia.getCronogramaCurso().getCurso().getNombre());
        dto.setNombreSede(asistencia.getCronogramaCurso().getSede().getNombreSede());
        dto.setDireccionSede(asistencia.getCronogramaCurso().getSede().getDireccionSede());
        dto.setHorario(asistencia.getCronogramaCurso().getHorario());
        dto.setFechaInicio(asistencia.getCronogramaCurso().getFechaInicio());
        dto.setFechaFin(asistencia.getCronogramaCurso().getFechaFin());
        dto.setFechaInscripcion(asistencia.getFecha());
        dto.setFechaBaja(asistencia.getFechaBaja());
        dto.setAprobado(asistencia.getAprobado());
        dto.setAsistencia(asistencia.getAsistencia());
        dto.setTotalClases(asistencia.getCronogramaCurso().getCantidadClases() != null ? 
                          asistencia.getCronogramaCurso().getCantidadClases() : 0);
        
        // Determinar estado
        if (asistencia.getFechaBaja() != null) {
            dto.setEstado("CANCELADO");
        } else if (asistencia.getCronogramaCurso().getFechaFin() != null && 
                   asistencia.getCronogramaCurso().getFechaFin().isBefore(java.time.LocalDate.now())) {
            dto.setEstado("FINALIZADO");
        } else {
            dto.setEstado("ACTIVO");
        }
        
        // Calcular monto pagado desde PagoCurso
        dto.setMontoPagado(asistencia.getCronogramaCurso().getCurso().getPrecio() != null ? 
                          BigDecimal.valueOf(asistencia.getCronogramaCurso().getCurso().getPrecio()) : BigDecimal.ZERO);
        
        return dto;
    }
}
