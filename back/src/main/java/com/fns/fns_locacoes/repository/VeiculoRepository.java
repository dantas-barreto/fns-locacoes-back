package com.fns.fns_locacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fns.fns_locacoes.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
