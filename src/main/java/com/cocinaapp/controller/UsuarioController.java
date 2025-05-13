package com.cocinaapp.controller;

import com.cocinaapp.model.Usuario;
import com.cocinaapp.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro/iniciar")
    public ResponseEntity<String> iniciarRegistro(@RequestParam String email, @RequestParam String alias) {
        try {
            String respuesta = usuarioService.iniciarRegistro(email, alias);
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/registro/completar")
    public ResponseEntity<String> completarRegistro(@RequestParam String email, @RequestParam String codigo, @RequestBody Usuario usuario) {
        try {
            boolean exito = usuarioService.completarRegistro(email, codigo, usuario);
            return exito ? ResponseEntity.ok("Registro completado con éxito.") : ResponseEntity.badRequest().body("Error en el registro.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}