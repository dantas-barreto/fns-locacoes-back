package com.fns.fns_locacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fns.fns_locacoes.model.Pagamento;
import com.fns.fns_locacoes.model.Locacao;
import com.fns.fns_locacoes.repository.PagamentoRepository;
import com.fns.fns_locacoes.repository.LocacaoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    public Pagamento salvar(Pagamento pagamento) {
        validarPagamento(pagamento);
        
        Locacao locacao = locacaoRepository.findById(pagamento.getLocacao().getId())
                .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
        pagamento.setLocacao(locacao);

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizar(Long id, Pagamento pagamentoAtualizado) {
        Pagamento existente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        validarPagamento(pagamentoAtualizado);

        existente.setData(pagamentoAtualizado.getData());
        existente.setValor(pagamentoAtualizado.getValor());
        existente.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());

        Locacao locacao = locacaoRepository.findById(pagamentoAtualizado.getLocacao().getId())
                .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
        existente.setLocacao(locacao);

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

    // Método de validação
    private void validarPagamento(Pagamento pagamento) {
        if (pagamento.getData() == null) {
            throw new RuntimeException("Data do pagamento é obrigatória");
        }
        if (pagamento.getValor() <= 0) {
            throw new RuntimeException("Valor do pagamento deve ser positivo");
        }
        if (pagamento.getMetodoPagamento() == null) {
            throw new RuntimeException("Método de pagamento é obrigatório");
        }
        if (pagamento.getLocacao() == null || pagamento.getLocacao().getId() == null) {
            throw new RuntimeException("Locação é obrigatória");
        }
    }
}
