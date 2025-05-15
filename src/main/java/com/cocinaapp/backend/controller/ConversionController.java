package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Conversion;
import com.cocinaapp.backend.service.ConversionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversiones")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @GetMapping
    public List<Conversion> obtenerTodasLasConversiones() {
        return conversionService.obtenerTodasLasConversiones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversion> obtenerConversionPorId(@PathVariable Integer id) {
        Optional<Conversion> conversion = conversionService.obtenerConversionPorId(id);
        return conversion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Conversion> guardarConversion(@RequestBody Conversion conversion) {
        Conversion conversionGuardada = conversionService.guardarConversion(conversion);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConversion(@PathVariable Integer id) {
        conversionService.eliminarConversion(id);
        return ResponseEntity.noContent().build();
    }
}
