package com.cocinaapp.service;

import com.cocinaapp.model.Utilizado;
import com.cocinaapp.repository.UtilizadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizadoService {

    @Autowired
    private UtilizadoRepository utilizadoRepository;

    public List<Utilizado> obtenerTodosLosUtilizados() {
        return utilizadoRepository.findAll();
    }

    public Optional<Utilizado> obtenerUtilizadoPorId(Integer id) {
        return utilizadoRepository.findById(id);
    }

    public Utilizado guardarUtilizado(Utilizado utilizado) {
        return utilizadoRepository.save(utilizado);
    }

    public void eliminarUtilizado(Integer id) {
        utilizadoRepository.deleteById(id);
    }
}
