package com.cocinaapp.repository;

import com.cocinaapp.model.Utilizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizadoRepository extends JpaRepository<Utilizado, Integer> {
}
