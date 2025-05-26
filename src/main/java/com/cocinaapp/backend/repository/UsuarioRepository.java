package com.cocinaapp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocinaapp.backend.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByNickname(String nickname);
    Optional<Usuario> findByMail(String email);
    Optional<Usuario> findByNickname(String nickname);
    
}