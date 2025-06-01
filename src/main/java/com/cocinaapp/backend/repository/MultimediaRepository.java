package com.cocinaapp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Multimedia;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
    List<Multimedia> findByPaso_IdPaso(Integer idPaso);
}
