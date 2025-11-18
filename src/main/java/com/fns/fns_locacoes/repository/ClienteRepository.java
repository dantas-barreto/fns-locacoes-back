package com.fns.fns_locacoes.repository;

import com.fns.fns_locacoes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
