package com.fns.fns_locacoes.repository;

import com.fns.fns_locacoes.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
}
