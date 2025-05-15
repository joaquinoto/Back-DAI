package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Conversion;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Integer> {
}
