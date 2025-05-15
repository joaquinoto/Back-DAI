package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.TipoReceta;
import com.cocinaapp.backend.service.TipoRecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tiposReceta")
public class TipoRecetaController {

    @Autowired
    private TipoRecetaService tipoRecetaService;

    @GetMapping
    public List<TipoReceta> obtenerTodosLosTipos() {
        return tipoRecetaService.obtenerTodosLosTipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoReceta> obtenerTipoPorId(@PathVariable Integer id) {
        Optional<TipoReceta> tipoReceta = tipoRecetaService.obtenerTipoPorId(id);
        return tipoReceta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoReceta> guardarTipo(@RequestBody TipoReceta tipoReceta) {
        TipoReceta tipoGuardado = tipoRecetaService.guardarTipo(tipoReceta);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Integer id) {
        tipoRecetaService.eliminarTipo(id);
        return ResponseEntity.noContent().build();
    }
}
