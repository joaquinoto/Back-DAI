package com.cocinaapp.service;

import com.cocinaapp.model.CodigoValidacion;
import com.cocinaapp.model.Usuario;
import com.cocinaapp.repository.CodigoValidacionRepository;
import com.cocinaapp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String iniciarRegistro(String email, String alias) {
        // Verificar si el alias ya existe
        if (usuarioRepository.existsByNickname(alias)) {
            List<String> sugerencias = sugerirAlias(alias);
            throw new IllegalArgumentException("El alias ya existe. Sugerencias: " + String.join(", ", sugerencias));
        }

        // Generar código de validación
        String codigo = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusHours(24);

        // Guardar el código en la base de datos
        CodigoValidacion codigoValidacion = new CodigoValidacion();
        codigoValidacion.setEmail(email);
        codigoValidacion.setCodigo(codigo);
        codigoValidacion.setFechaExpiracion(expiracion);
        codigoValidacionRepository.save(codigoValidacion);

        // Enviar correo (simulado aquí)
        enviarCorreo(email, "Código de validación", "Tu código es: " + codigo);

        return "Código enviado al correo: " + email;
    }

    public boolean completarRegistro(String email, String codigo, Usuario usuario) {
        Optional<CodigoValidacion> validacion = codigoValidacionRepository.findByEmail(email);

        if (validacion.isEmpty() || !validacion.get().getCodigo().equals(codigo)) {
            throw new IllegalArgumentException("Código inválido o expirado.");
        }

        if (validacion.get().getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El código ha expirado.");
        }

        // Guardar el usuario
        usuarioRepository.save(usuario);

        // Eliminar el código usado
        codigoValidacionRepository.delete(validacion.get());

        return true;
    }

    private void enviarCorreo(String email, String asunto, String mensaje) {
        // Aquí iría la lógica para enviar correos (usando JavaMailSender o similar)
        System.out.println("Enviando correo a " + email + ": " + mensaje);
    }

    private List<String> sugerirAlias(String alias) {
        return List.of(alias + "123", alias + "_user", alias + "2025");
    }
}