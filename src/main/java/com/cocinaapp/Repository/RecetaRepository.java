package com.cocinaapp.repository;

import com.cocinaapp.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    List<Receta> findByNombreRecetaContainingIgnoreCase(String nombreReceta);
    List<Receta> findByTipoReceta_IdTipo(Integer idTipo);
    List<Receta> findByUsuario_IdUsuario(Integer idUsuario);
    List<Receta> findByIngredientes_NombreIgnoreCase(String nombreIngrediente);
    List<Receta> findByIngredientes_NombreNotIgnoreCase(String nombreIngrediente);
}