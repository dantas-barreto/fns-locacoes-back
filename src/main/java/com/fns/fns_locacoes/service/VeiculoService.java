package com.fns.fns_locacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fns.fns_locacoes.model.Veiculo;
import com.fns.fns_locacoes.repository.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public Veiculo atualizar(Long id, Veiculo veiculoAtualizado) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        existente.setModelo(veiculoAtualizado.getModelo());
        existente.setMarca(veiculoAtualizado.getModelo());
        existente.setPlaca(veiculoAtualizado.getPlaca());
        existente.setAno(veiculoAtualizado.getAno());
        existente.setRenavam(veiculoAtualizado.getRenavam());
        existente.setStatusVeiculo(veiculoAtualizado.getStatusVeiculo());

        return veiculoRepository.save(existente);
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public List<Veiculo> listar() {
        return veiculoRepository.findAll();
    }

    public void remover(Long id) {
        veiculoRepository.deleteById(id);
    }
}
