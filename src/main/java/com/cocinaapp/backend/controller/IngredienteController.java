package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Ingrediente;
import com.cocinaapp.backend.service.IngredienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public List<Ingrediente> obtenerTodosLosIngredientes() {
        return ingredienteService.obtenerTodosLosIngredientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> obtenerIngredientePorId(@PathVariable Integer id) {
        Optional<Ingrediente> ingrediente = ingredienteService.obtenerIngredientePorId(id);
        return ingrediente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingrediente> guardarIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente ingredienteGuardado = ingredienteService.guardarIngrediente(ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Integer id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}
