package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.AsistenciaCurso;
import com.cocinaapp.backend.model.CronogramaCurso;
import com.cocinaapp.backend.model.RegistroAsistencia;
import com.cocinaapp.backend.model.PagoCurso;
import com.cocinaapp.backend.repository.AlumnoRepository;
import com.cocinaapp.backend.repository.AsistenciaCursoRepository;
import com.cocinaapp.backend.repository.CronogramaCursoRepository;
import com.cocinaapp.backend.repository.RegistroAsistenciaRepository;
import com.cocinaapp.backend.repository.PagoCursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AsistenciaCursoService {

    @Autowired
    private AsistenciaCursoRepository asistenciaCursoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private CronogramaCursoRepository cronogramaCursoRepository;
    @Autowired
    private RegistroAsistenciaRepository registroAsistenciaRepository;
    @Autowired
    private PagoCursoRepository pagoCursoRepository;

    public void inscribirAlumno(int idAlumno, int idCronograma) {
        if (asistenciaCursoRepository.existsByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(idAlumno, idCronograma)) {
            throw new IllegalArgumentException("Ya estás inscripto en este curso.");
        }
        Alumno alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));
        CronogramaCurso cronograma = cronogramaCursoRepository.findById(idCronograma)
                .orElseThrow(() -> new IllegalArgumentException("Cronograma no encontrado"));

        // Lógica de pago
        double monto = cronograma.getCurso().getPrecio();
        PagoCurso pago = new PagoCurso();
        pago.setAlumno(alumno);
        pago.setCronogramaCurso(cronograma);
        pago.setMonto(monto);
        pago.setTipo("PAGO");
        pago.setFecha(LocalDateTime.now());
        pagoCursoRepository.save(pago);

        // Disminuir vacantes
        if (cronograma.getVacantesDisponibles() != null && cronograma.getVacantesDisponibles() > 0) {
            cronograma.setVacantesDisponibles(cronograma.getVacantesDisponibles() - 1);
            cronogramaCursoRepository.save(cronograma);
        }

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
        CronogramaCurso cronograma = asistencia.getCronogramaCurso();
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = cronograma.getFechaInicio();
        long dias = ChronoUnit.DAYS.between(hoy, inicio);

        double monto = cronograma.getCurso().getPrecio();
        double reintegro = 0.0;
        if (dias > 10) {
            reintegro = monto;
        } else if (dias >= 1 && dias <= 9) {
            reintegro = monto * 0.7;
        } else if (dias == 0) {
            reintegro = monto * 0.5;
        }

        if (reintegro > 0) {
            PagoCurso pago = new PagoCurso();
            pago.setAlumno(asistencia.getAlumno());
            pago.setCronogramaCurso(cronograma);
            pago.setMonto(reintegro);
            pago.setTipo("REINTEGRO");
            pago.setFecha(LocalDateTime.now());
            pagoCursoRepository.save(pago);

            // Sumar el reintegro a la cuenta corriente del alumno
            Alumno alumno = asistencia.getAlumno();
            alumno.setCuentaCorriente(alumno.getCuentaCorriente() + reintegro);
            alumnoRepository.save(alumno);
        }

        asistencia.setFechaBaja(LocalDateTime.now());
        asistenciaCursoRepository.save(asistencia);

        // Aumentar vacantes
        if (cronograma.getVacantesDisponibles() != null) {
            cronograma.setVacantesDisponibles(cronograma.getVacantesDisponibles() + 1);
            cronogramaCursoRepository.save(cronograma);
        }
    }

    public List<AsistenciaCurso> cursosInscripto(int idAlumno) {
        return asistenciaCursoRepository.findByAlumno_IdAlumnoAndFechaBajaIsNull(idAlumno);
    }

    public void registrarAsistencia(int idAlumno, int idCronograma) {
        AsistenciaCurso asistencia = asistenciaCursoRepository
            .findByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(idAlumno, idCronograma);
        if (asistencia == null) {
            throw new IllegalArgumentException("El alumno no está inscripto en este curso.");
        }
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setAsistenciaCurso(asistencia);
        registro.setFechaHora(java.time.LocalDateTime.now());
        registroAsistenciaRepository.save(registro);
    }

    public AsistenciaCurso buscarAsistenciaCurso(int idAlumno, int idCronograma) {
        return asistenciaCursoRepository
            .findByAlumno_IdAlumnoAndCronogramaCurso_IdCronogramaAndFechaBajaIsNull(idAlumno, idCronograma);
    }

    public List<RegistroAsistencia> obtenerHistorialAsistencia(Integer idAsistenciaCurso) {
        return registroAsistenciaRepository.findByAsistenciaCurso_Id(idAsistenciaCurso);
    }
}