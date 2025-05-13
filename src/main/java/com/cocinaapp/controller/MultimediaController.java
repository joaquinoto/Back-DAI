package com.cocinaapp.controller;

import com.cocinaapp.model.Multimedia;
import com.cocinaapp.service.MultimediaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {

    @Autowired
    private MultimediaService multimediaService;

    @GetMapping
    public List<Multimedia> obtenerTodaLaMultimedia() {
        return multimediaService.obtenerTodaLaMultimedia();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multimedia> obtenerMultimediaPorId(@PathVariable Integer id) {
        Optional<Multimedia> multimedia = multimediaService.obtenerMultimediaPorId(id);
        return multimedia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Multimedia> guardarMultimedia(@RequestBody Multimedia multimedia) {
        Multimedia multimediaGuardada = multimediaService.guardarMultimedia(multimedia);
        return ResponseEntity.status(HttpStatus.CREATED).body(multimediaGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMultimedia(@PathVariable Integer id) {
        multimediaService.eliminarMultimedia(id);
        return ResponseEntity.noContent().build();
    }
}
