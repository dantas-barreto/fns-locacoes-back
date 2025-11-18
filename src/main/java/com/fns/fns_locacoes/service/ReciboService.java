package com.fns.fns_locacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fns.fns_locacoes.model.Recibo;
import com.fns.fns_locacoes.repository.ReciboRepository;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    public Recibo salvar(Recibo recibo) {
        return reciboRepository.save(recibo);
    }

    public Recibo atualizar(Long id, Recibo reciboAtualizado) {
        Recibo existente = reciboRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recibo não encontrado"));

        existente.setDataEmissao(reciboAtualizado.getDataEmissao());

        return reciboRepository.save(existente);
    }

    public Recibo buscarPorId(Long id) {
        return reciboRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recibo não encontrado"));
    }

    public List<Recibo> listar() {
        return reciboRepository.findAll();
    }

    public void remover(Long id) {
        reciboRepository.deleteById(id);
    }
}
