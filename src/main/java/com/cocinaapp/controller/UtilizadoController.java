package com.cocinaapp.controller;

import com.cocinaapp.model.Utilizado;
import com.cocinaapp.service.UtilizadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilizados")
public class UtilizadoController {

    @Autowired
    private UtilizadoService utilizadoService;

    @GetMapping
    public List<Utilizado> obtenerTodosLosUtilizados() {
        return utilizadoService.obtenerTodosLosUtilizados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilizado> obtenerUtilizadoPorId(@PathVariable Integer id) {
        Optional<Utilizado> utilizado = utilizadoService.obtenerUtilizadoPorId(id);
        return utilizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utilizado> guardarUtilizado(@RequestBody Utilizado utilizado) {
        Utilizado utilizadoGuardado = utilizadoService.guardarUtilizado(utilizado);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilizadoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUtilizado(@PathVariable Integer id) {
        utilizadoService.eliminarUtilizado(id);
        return ResponseEntity.noContent().build();
    }
}
