package com.cocinaapp.service;

import com.cocinaapp.model.Foto;
import com.cocinaapp.repository.FotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    public List<Foto> obtenerTodasLasFotos() {
        return fotoRepository.findAll();
    }

    public Optional<Foto> obtenerFotoPorId(Integer id) {
        return fotoRepository.findById(id);
    }

    public Foto guardarFoto(Foto foto) {
        return fotoRepository.save(foto);
    }

    public void eliminarFoto(Integer id) {
        fotoRepository.deleteById(id);
    }
}
