package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.AsistenciaCurso;
import com.cocinaapp.backend.model.CronogramaCurso;
import com.cocinaapp.backend.repository.AlumnoRepository;
import com.cocinaapp.backend.repository.AsistenciaCursoRepository;
import com.cocinaapp.backend.repository.CronogramaCursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsistenciaCursoService {

    @Autowired
    private AsistenciaCursoRepository asistenciaCursoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private CronogramaCursoRepository cronogramaCursoRepository;

    public void inscribirAlumno(int idAlumno, int idCronograma) {
        if (asistenciaCursoRepository.existsByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(idAlumno, idCronograma)) {
            throw new IllegalArgumentException("Ya estás inscripto en este curso.");
        }
        Alumno alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));
        CronogramaCurso cronograma = cronogramaCursoRepository.findById(idCronograma)
                .orElseThrow(() -> new IllegalArgumentException("Cronograma no encontrado"));

        AsistenciaCurso asistencia = new AsistenciaCurso();
        asistencia.setAlumno(alumno);
        asistencia.setCronogramaCurso(cronograma);
        asistencia.setFecha(LocalDateTime.now());
        asistenciaCursoRepository.save(asistencia);
    }

    public void bajaAlumno(int idAlumno, int idCronograma) {
        AsistenciaCurso asistencia = asistenciaCursoRepository.findByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(idAlumno, idCronograma);
        if (asistencia == null) {
            throw new IllegalArgumentException("No estás inscripto en este curso.");
        }
        asistencia.setFechaBaja(LocalDateTime.now());
        asistenciaCursoRepository.save(asistencia);
    }

    public List<AsistenciaCurso> cursosInscripto(int idAlumno) {
        return asistenciaCursoRepository.findByAlumno_IdAlumnoAndFechaBajaIsNull(idAlumno);
    }
}