package com.fns.fns_locacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fns.fns_locacoes.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}
