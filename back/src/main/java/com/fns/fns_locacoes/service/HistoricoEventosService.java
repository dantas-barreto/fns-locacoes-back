package com.fns.fns_locacoes.service;

import com.fns.fns_locacoes.model.HistoricoEventos;
import com.fns.fns_locacoes.repository.HistoricoEventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoEventosService {

    @Autowired
    private HistoricoEventosRepository historicoEventosRepository;

    public HistoricoEventos salvar(HistoricoEventos historicoEventos) {
        return historicoEventosRepository.save(historicoEventos);
    }

    public HistoricoEventos atualizar(Long id, HistoricoEventos historicoEventosAtualizada) {
        HistoricoEventos existente = historicoEventosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("historicoEventos não encontrada"));

        existente.setObservacao(historicoEventosAtualizada.getObservacao());
        existente.setData(historicoEventosAtualizada.getData());
        existente.setStatus(historicoEventosAtualizada.getStatus());

        return historicoEventosRepository.save(existente);
    }

    public HistoricoEventos buscarPorId(Long id) {
        return historicoEventosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("historicoEventos não encontrada"));
    }

    public List<HistoricoEventos> listar() {
        return historicoEventosRepository.findAll();
    }

    public void remover (Long id) {
        historicoEventosRepository.deleteById(id);
    }
}
