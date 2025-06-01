package com.cocinaapp.backend.repository;

import com.cocinaapp.backend.model.RegistroAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Integer> {
    List<RegistroAsistencia> findByAsistenciaCurso_IdAsistencia(Integer idAsistenciaCurso);
    int countByAsistenciaCurso_IdAsistencia(Integer idAsistenciaCurso);
}