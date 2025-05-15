package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.RegistroRequest;
import com.cocinaapp.backend.model.Usuario;
import com.cocinaapp.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.cocinaapp.backend.repository.UsuarioRepository usuarioRepository;

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
        @RequestBody RegistroRequest registroRequest) {
    try {
        boolean exito = usuarioService.completarRegistro(
            email,
            codigo,
            registroRequest.getUsuario(),
            registroRequest.getAlumno(),
            esAlumno
        );
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

    @PostMapping("/convertir-a-alumno")
    public ResponseEntity<String> convertirAAlumno(
            @RequestParam int idUsuario,
            @RequestBody Alumno datosAlumno) {
        try {
            usuarioService.convertirAAlumno(idUsuario, datosAlumno);
            return ResponseEntity.ok("Usuario convertido a alumno exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/liberar-mail")
    public ResponseEntity<String> liberarMailPendiente(@RequestParam String email) {
        try {
            usuarioService.liberarMailPendiente(email);
            return ResponseEntity.ok("Mail liberado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/registro/admin-dev") //para probar admin
    public ResponseEntity<String> crearAdminDev(@RequestBody Usuario usuario) {
        try {
            usuario.setRol("ADMIN");
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Admin creado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear admin: " + e.getMessage());
        }
    }
}