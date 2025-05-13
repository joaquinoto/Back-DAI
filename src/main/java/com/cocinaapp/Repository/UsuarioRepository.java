package com.cocinaapp.repository;

import com.cocinaapp.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByNickname(String nickname);
    Optional<Usuario> findByMail(String mail);
}
