package com.fns.fns_locacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fns.fns_locacoes.model.Pagamento;
import com.fns.fns_locacoes.repository.PagamentoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizar(Long id, Pagamento pagamentoAtualizado) {
        Pagamento existente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        existente.setData(pagamentoAtualizado.getData());
        existente.setValor(pagamentoAtualizado.getValor());
        existente.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());

        return pagamentoRepository.save(existente);
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public List<Pagamento> listar() {
        return pagamentoRepository.findAll();
    }

    public void remover(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
