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
    public ResponseEntity<String> completarRegistro(
            @RequestParam String email,
            @RequestParam String codigo,
            @RequestParam(defaultValue = "false") boolean esAlumno,
            @RequestBody Usuario usuario) {
        try {
            boolean exito = usuarioService.completarRegistro(email, codigo, usuario, esAlumno);
            return exito ? ResponseEntity.ok("Registro completado con éxito.") : ResponseEntity.badRequest().body("Error en el registro.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recuperar/solicitar")
    public ResponseEntity<String> solicitarRecuperacion(@RequestParam String email) {
        try {
            String respuesta = usuarioService.solicitarRecuperacion(email);
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recuperar/completar")
    public ResponseEntity<String> completarRecuperacion(
            @RequestParam String email,
            @RequestParam String codigo,
            @RequestParam String nuevaContrasena) {
        try {
            boolean exito = usuarioService.completarRecuperacion(email, codigo, nuevaContrasena);
            return exito ? ResponseEntity.ok("Contraseña actualizada con éxito.") : ResponseEntity.badRequest().body("Error en la recuperación.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}