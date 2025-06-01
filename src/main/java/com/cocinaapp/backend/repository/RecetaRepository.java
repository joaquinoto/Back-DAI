package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Receta;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    
    List<Receta> findTop3ByOrderByFechaCreacionDesc();
    List<Receta> findByNombreRecetaContainingIgnoreCaseAndAprobadoTrue(String nombreReceta, Sort sort);
    List<Receta> findByTipoReceta_IdTipoAndAprobadoTrue(Integer idTipo, Sort sort);
    List<Receta> findByUsuario_IdUsuario(Integer idUsuario, Sort sort);
    List<Receta> findByIngredientes_NombreIgnoreCaseAndAprobadoTrue(String nombreIngrediente, Sort sort);
    List<Receta> findByIngredientes_NombreNotContainingIgnoreCaseAndAprobadoTrue(String nombreIngrediente, Sort sort);
    List<Receta> findByAprobadoTrue();
    Optional<Receta> findByNombreRecetaAndUsuario_IdUsuario(String nombreReceta, Integer idUsuario);
}