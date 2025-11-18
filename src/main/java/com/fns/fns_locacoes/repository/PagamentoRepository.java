package com.fns.fns_locacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fns.fns_locacoes.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
