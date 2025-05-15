package com.cocinaapp.backend.security;

import com.cocinaapp.backend.model.Usuario;
import com.cocinaapp.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + mail));

        return User.builder()
                .username(usuario.getMail())
                .password(usuario.getContrasena())
                .roles(usuario.getRol().toUpperCase())
                .build();
    }
}