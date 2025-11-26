package com.fns.fns_locacoes.service;

import com.fns.fns_locacoes.model.Mensagem;
import com.fns.fns_locacoes.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public Mensagem salvar(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public Mensagem atualizar(Long id, Mensagem mensagemAtualizada) {
        Mensagem existente = mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        existente.setObservacao(mensagemAtualizada.getObservacao());
        existente.setData(mensagemAtualizada.getData());
        existente.setStatus(mensagemAtualizada.getStatus());

        return mensagemRepository.save(existente);
    }

    public Mensagem buscarPorId(Long id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
    }

    public List<Mensagem> listar() {
        return mensagemRepository.findAll();
    }

    public void remover (Long id) {
        mensagemRepository.deleteById(id);
    }
}
