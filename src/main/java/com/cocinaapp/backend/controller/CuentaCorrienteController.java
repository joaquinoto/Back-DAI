package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.EstadoCuentaDTO;
import com.cocinaapp.backend.service.CuentaCorrienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cuenta-corriente")
public class CuentaCorrienteController {
    
    @Autowired
    private CuentaCorrienteService cuentaCorrienteService;
    
    @GetMapping("/alumno/{idAlumno}")
    public ResponseEntity<EstadoCuentaDTO> obtenerEstadoCuenta(@PathVariable Integer idAlumno) {
        EstadoCuentaDTO estado = cuentaCorrienteService.obtenerEstadoCuenta(idAlumno);
        return ResponseEntity.ok(estado);
    }
    
    @GetMapping("/saldo/{idAlumno}")
    public ResponseEntity<BigDecimal> obtenerSaldo(@PathVariable Integer idAlumno) {
        BigDecimal saldo = cuentaCorrienteService.obtenerSaldoActual(idAlumno);
        return ResponseEntity.ok(saldo);
    }
}
