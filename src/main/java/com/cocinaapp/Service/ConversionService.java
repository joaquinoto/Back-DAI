package com.cocinaapp.service;

import com.cocinaapp.model.Conversion;
import com.cocinaapp.repository.ConversionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversionService {

    @Autowired
    private ConversionRepository conversionRepository;

    public List<Conversion> obtenerTodasLasConversiones() {
        return conversionRepository.findAll();
    }

    public Optional<Conversion> obtenerConversionPorId(Integer id) {
        return conversionRepository.findById(id);
    }

    public Conversion guardarConversion(Conversion conversion) {
        return conversionRepository.save(conversion);
    }

    public void eliminarConversion(Integer id) {
        conversionRepository.deleteById(id);
    }
}
