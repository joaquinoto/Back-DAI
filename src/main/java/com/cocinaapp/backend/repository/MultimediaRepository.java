package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Multimedia;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
}
