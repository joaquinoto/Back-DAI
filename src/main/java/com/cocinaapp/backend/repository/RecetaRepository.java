package com.cocinaapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Receta;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    List<Receta> findByNombreRecetaContainingIgnoreCaseAndAprobadoTrue(String nombreReceta);
    List<Receta> findByTipoReceta_IdTipoAndAprobadoTrue(Integer idTipo);
    List<Receta> findByUsuario_IdUsuario(Integer idUsuario);
    List<Receta> findByAprobadoTrue();
    List<Receta> findByIngredientes_NombreIgnoreCaseAndAprobadoTrue(String nombreIngrediente);
    List<Receta> findByIngredientes_NombreNotIgnoreCaseAndAprobadoTrue(String nombreIngrediente);
    Optional<Receta> findByNombreRecetaAndUsuario_IdUsuario(String nombreReceta, Integer idUsuario);


}