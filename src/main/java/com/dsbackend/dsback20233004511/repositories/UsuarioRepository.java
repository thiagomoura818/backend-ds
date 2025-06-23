package com.dsbackend.dsback20233004511.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsbackend.dsback20233004511.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByLogin(String login);
	boolean existsByLogin(String login);
}
