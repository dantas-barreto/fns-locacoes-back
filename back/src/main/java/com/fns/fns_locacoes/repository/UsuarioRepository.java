package com.fns.fns_locacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fns.fns_locacoes.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
