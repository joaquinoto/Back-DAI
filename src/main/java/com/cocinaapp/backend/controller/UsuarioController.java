package com.cocinaapp.backend.controller;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.CodigoValidacion;
import com.cocinaapp.backend.model.LoginRequest;
import com.cocinaapp.backend.model.RegistroRequest;
import com.cocinaapp.backend.model.Usuario;
import com.cocinaapp.backend.repository.CodigoValidacionRepository;
import com.cocinaapp.backend.security.JwtService;
import com.cocinaapp.backend.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private JwtService jwtService;

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
    public ResponseEntity<String> completarRegistro(@RequestBody RegistroRequest registroRequest) {
        try {
            System.out.println("DEBUG UsuarioController: Avatar en usuario (controller): " +
                (registroRequest.getUsuario() != null && registroRequest.getUsuario().getAvatar() != null
                    ? registroRequest.getUsuario().getAvatar().length
                    : "null"));
            boolean exito = usuarioService.completarRegistro(
                registroRequest.getEmail(),
                registroRequest.getCodigo(),
                registroRequest.getUsuario(),
                registroRequest.getAlumno(),
                registroRequest.isEsAlumno()
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String identificador = loginRequest.getIdentificador();
        String contrasena = loginRequest.getContrasena();

        Usuario user = usuarioRepository.findByMail(identificador)
            .orElseGet(() -> usuarioRepository.findByNickname(identificador).orElse(null));

        if (user == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado.");
        }
        if (!passwordEncoder.matches(contrasena, user.getContrasena())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta.");
        }

        // Generar el token JWT
        String token = jwtService.generateToken(
            user.getMail(), // o user.getNickname() según tu lógica
            Map.of("rol", user.getRol())
        );

        // Devolver el token y datos básicos del usuario
        return ResponseEntity.ok(Map.of(
            "token", token,
            "usuario", user.getNickname(),
            "rol", user.getRol(),
            "id", user.getIdUsuario()
        ));
    }

    @GetMapping("/sugerir-alias")
    public ResponseEntity<List<String>> sugerirAliasDisponibles(@RequestParam String alias) {
        return ResponseEntity.ok(usuarioService.sugerirAliasDisponibles(alias));
    }
    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> obtenerAvatar(@PathVariable int id) {
        return usuarioService.obtenerAvatarPorId(id)
                .filter(avatar -> avatar != null)
                .map(avatar -> ResponseEntity.ok()
                        .header("Content-Type", "image/jpeg") 
                        .body(avatar))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/perfil")
    public ResponseEntity<?> actualizarPerfil(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        try {
            usuarioService.actualizarPerfil(id, updates);
            return ResponseEntity.ok("Perfil actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
private CodigoValidacionRepository codigoValidacionRepository;

    @PostMapping("/registro/verificar-codigo")
    public ResponseEntity<?> verificarCodigo(
            @RequestParam String email,
            @RequestParam String codigo
    ) {
        java.util.Optional<CodigoValidacion> validacionOpt = codigoValidacionRepository.findAllByEmailAndUsadoFalse(email)
            .stream().findFirst();

        if (validacionOpt.isEmpty() || !validacionOpt.get().getCodigo().equals(codigo)) {
            return ResponseEntity.status(400).body("Código inválido o expirado.");
        }
        CodigoValidacion validacion = validacionOpt.get();
        if (validacion.getFechaExpiracion().isBefore(java.time.LocalDateTime.now())) {
            return ResponseEntity.status(400).body("El código ha expirado.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recuperar/verificar-codigo")
    public ResponseEntity<?> verificarCodigoRecuperacion(@RequestParam String email, @RequestParam String codigo) {
        Optional<CodigoValidacion> validacion = codigoValidacionRepository.findAllByEmailAndUsadoFalse(email)
            .stream()
            .filter(c -> "RECUPERACION".equals(c.getTipo()) && c.getCodigo().equals(codigo))
            .findFirst();

        if (validacion.isEmpty() || validacion.get().getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido o expirado.");
        }
        return ResponseEntity.ok().build();
    }

}