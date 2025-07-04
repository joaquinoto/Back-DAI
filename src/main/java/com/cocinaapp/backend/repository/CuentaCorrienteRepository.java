package com.cocinaapp.backend.repository;

import com.cocinaapp.backend.model.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Integer> {
    
    List<CuentaCorriente> findByAlumno_IdAlumnoOrderByFechaMovimientoDesc(Integer idAlumno);
    
    CuentaCorriente findTopByAlumno_IdAlumnoOrderByFechaMovimientoDesc(Integer idAlumno);
}
