package com.cocinaapp.backend.service;

import com.cocinaapp.backend.model.Alumno;
import com.cocinaapp.backend.model.CodigoValidacion;
import com.cocinaapp.backend.model.Usuario;
import com.cocinaapp.backend.repository.CodigoValidacionRepository;
import com.cocinaapp.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoValidacionRepository codigoValidacionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private com.cocinaapp.backend.repository.AlumnoRepository alumnoRepository;

    // Primera etapa: iniciar registro
    public String iniciarRegistro(String email, String alias) {
        if (usuarioRepository.findByMail(email).isPresent()) {
            throw new IllegalArgumentException("El mail ya está registrado. ¿Olvidaste tu clave?");
        }
        if (codigoValidacionRepository.existsByEmailAndUsadoFalse(email)) {
            throw new IllegalArgumentException("El mail está pendiente de validación. Contacta a soporte para liberarlo.");
        }
        if (usuarioRepository.existsByNickname(alias) || codigoValidacionRepository.existsByAliasAndUsadoFalse(alias)) {
            List<String> sugerencias = sugerirAliasDisponibles(alias);
            throw new IllegalArgumentException("El alias ya existe. Sugerencias: " + String.join(", ", sugerencias));
        }

        String codigo = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime ahora = LocalDateTime.now();

        CodigoValidacion codigoValidacion = new CodigoValidacion();
        codigoValidacion.setEmail(email);
        codigoValidacion.setCodigo(codigo);
        codigoValidacion.setFechaExpiracion(ahora.plusHours(24));
        codigoValidacion.setFechaCreacion(ahora);
        codigoValidacion.setUsado(false);
        codigoValidacionRepository.save(codigoValidacion);

        enviarCorreo(email, "Código de validación",
            "<h3>Tu código de validación es:</h3><p><b>" + codigo + "</b></p>");

        return "Código enviado al correo: " + email;
    }

        public boolean completarRegistro(String email, String codigo, Usuario usuario, Alumno alumno, boolean esAlumno) {
        Optional<CodigoValidacion> validacion = codigoValidacionRepository.findAllByEmailAndUsadoFalse(email)
            .stream().findFirst();

        if (validacion.isEmpty() || !validacion.get().getCodigo().equals(codigo)) {
            throw new IllegalArgumentException("Código inválido o expirado.");
        }
        if (validacion.get().getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El código ha expirado.");
        }

        if (esAlumno) {
            // Validar datos obligatorios de alumno
            if (
                usuario.getNombre() == null || usuario.getContrasena() == null ||
                alumno == null ||
                alumno.getNumeroTarjeta() == null ||
                alumno.getDniFrente() == null ||
                alumno.getDniFondo() == null ||
                alumno.getTramite() == null
            ) {
                throw new IllegalArgumentException("Faltan datos obligatorios para alumno (nombre, contraseña, tarjeta, fotos de DNI, trámite).");
            }
            usuario.setRol("ALUMNO");
        } else {
            usuario.setRol("USUARIO");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Si es alumno, guardar también en la tabla alumnos
        if (esAlumno) {
            alumno.setIdAlumno(usuarioGuardado.getIdUsuario());
            alumno.setCuentaCorriente(0.0);
            alumno.setUsuario(usuarioGuardado);
            alumnoRepository.save(alumno);
        }

        CodigoValidacion cod = validacion.get();
        cod.setUsado(true);
        codigoValidacionRepository.save(cod);

        return true;
    }

    // Sugerir alias que no estén en uso ni en validación pendiente
    public List<String> sugerirAliasDisponibles(String alias) {
        List<String> posibles = List.of(alias + "123", alias + "_user", alias + "2025");
        return posibles.stream()
            .filter(a -> !usuarioRepository.existsByNickname(a) && !codigoValidacionRepository.existsByAliasAndUsadoFalse(a))
            .toList();
    }

    private void enviarCorreo(String email, String asunto, String mensaje) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(asunto);
            helper.setText(mensaje, true); // true para HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("No se pudo enviar el correo: " + e.getMessage());
        }
    }

    public String solicitarRecuperacion(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByMail(email);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("El mail no está registrado.");
        }

        // Invalida códigos anteriores de recuperación
        codigoValidacionRepository.findAllByEmailAndUsadoFalse(email)
            .stream()
            .filter(c -> "RECUPERACION".equals(c.getTipo()))
            .findFirst()
            .ifPresent(c -> {
                c.setUsado(true);
                codigoValidacionRepository.save(c);
            });

        String codigo = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime ahora = LocalDateTime.now();

        CodigoValidacion codigoValidacion = new CodigoValidacion();
        codigoValidacion.setEmail(email);
        codigoValidacion.setCodigo(codigo);
        codigoValidacion.setFechaExpiracion(ahora.plusMinutes(30));
        codigoValidacion.setFechaCreacion(ahora);
        codigoValidacion.setUsado(false);
        codigoValidacion.setTipo("RECUPERACION");
        codigoValidacionRepository.save(codigoValidacion);

        enviarCorreo(email, "Recuperación de contraseña",
            "<h3>Tu código de recuperación es:</h3><p><b>" + codigo + "</b></p>");

        return "Código de recuperación enviado al correo: " + email;
    }

    public boolean completarRecuperacion(String email, String codigo, String nuevaContrasena) {
    Optional<CodigoValidacion> validacion = codigoValidacionRepository.findAllByEmailAndUsadoFalse(email)
        .stream()
        .filter(c -> "RECUPERACION".equals(c.getTipo()) && c.getCodigo().equals(codigo))
        .findFirst();

    if (validacion.isEmpty()) {
        throw new IllegalArgumentException("Código inválido o expirado.");
    }
    if (validacion.get().getFechaExpiracion().isBefore(LocalDateTime.now())) {
        throw new IllegalArgumentException("El código ha expirado.");
    }

    Usuario usuario = usuarioRepository.findByMail(email)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

    usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
    usuarioRepository.save(usuario);

    CodigoValidacion cod = validacion.get();
    cod.setUsado(true);
    codigoValidacionRepository.save(cod);

    return true;
}

    public void convertirAAlumno(int idUsuario, Alumno datosAlumno) {
    Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    if (alumnoRepository.existsById(idUsuario)) {
        throw new IllegalArgumentException("El usuario ya es alumno.");
    }

    // Validar datos obligatorios
    if (datosAlumno.getNumeroTarjeta() == null ||
        datosAlumno.getDniFrente() == null ||
        datosAlumno.getDniFondo() == null ||
        datosAlumno.getTramite() == null) {
        throw new IllegalArgumentException("Faltan datos obligatorios para alumno.");
    }

    usuario.setRol("ALUMNO");
    usuarioRepository.save(usuario);

    datosAlumno.setIdAlumno(idUsuario);
    datosAlumno.setCuentaCorriente(0.0);
    datosAlumno.setUsuario(usuario);
    alumnoRepository.save(datosAlumno);
}

    public void liberarMailPendiente(String email) {
    List<CodigoValidacion> codigos = codigoValidacionRepository.findAllByEmailAndUsadoFalse(email);
    if (codigos.isEmpty()) {
        throw new IllegalArgumentException("No hay registros pendientes para este mail.");
    }
    codigos.forEach(c -> {
        c.setUsado(true);
        codigoValidacionRepository.save(c);
    });
}
}