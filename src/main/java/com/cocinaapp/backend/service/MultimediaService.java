package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Multimedia;
import com.cocinaapp.backend.repository.MultimediaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaService {

    @Autowired
    private MultimediaRepository multimediaRepository;

    public List<Multimedia> obtenerTodaLaMultimedia() {
        return multimediaRepository.findAll();
    }

    public Optional<Multimedia> obtenerMultimediaPorId(Integer id) {
        return multimediaRepository.findById(id);
    }

    public Multimedia guardarMultimedia(Multimedia multimedia) {
        return multimediaRepository.save(multimedia);
    }

    public void eliminarMultimedia(Integer id) {
        multimediaRepository.deleteById(id);
    }
}
