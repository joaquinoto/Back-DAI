package com.cocinaapp.controller;

import com.cocinaapp.model.Foto;
import com.cocinaapp.service.FotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fotos")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @GetMapping
    public List<Foto> obtenerTodasLasFotos() {
        return fotoService.obtenerTodasLasFotos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foto> obtenerFotoPorId(@PathVariable Integer id) {
        Optional<Foto> foto = fotoService.obtenerFotoPorId(id);
        return foto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Foto> guardarFoto(@RequestBody Foto foto) {
        Foto fotoGuardada = fotoService.guardarFoto(foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fotoGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Integer id) {
        fotoService.eliminarFoto(id);
        return ResponseEntity.noContent().build();
    }
}
