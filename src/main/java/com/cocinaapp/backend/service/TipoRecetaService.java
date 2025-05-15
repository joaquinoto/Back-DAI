package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.TipoReceta;
import com.cocinaapp.backend.repository.TipoRecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoRecetaService {

    @Autowired
    private TipoRecetaRepository tipoRecetaRepository;

    public List<TipoReceta> obtenerTodosLosTipos() {
        return tipoRecetaRepository.findAll();
    }

    public Optional<TipoReceta> obtenerTipoPorId(Integer id) {
        return tipoRecetaRepository.findById(id);
    }

    public TipoReceta guardarTipo(TipoReceta tipoReceta) {
        return tipoRecetaRepository.save(tipoReceta);
    }

    public void eliminarTipo(Integer id) {
        tipoRecetaRepository.deleteById(id);
    }
}
