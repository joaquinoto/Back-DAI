package com.cocinaapp.backend.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class EstadoCuentaDTO {
    private BigDecimal saldoActual;
    private List<CuentaCorrienteDTO> movimientos;
    private List<CursoInscriptoDTO> cursosInscriptos;
}
