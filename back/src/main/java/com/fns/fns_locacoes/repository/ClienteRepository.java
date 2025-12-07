package com.fns.fns_locacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fns.fns_locacoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
