package com.fns.fns_locacoes.service;

import com.fns.fns_locacoes.model.Cliente;
import com.fns.fns_locacoes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        existente.setNome(clienteAtualizado.getNome());
        existente.setCpf(clienteAtualizado.getCpf());
        existente.setTelefone(clienteAtualizado.getTelefone());
        existente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(existente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public void remover(Long id) {
        clienteRepository.deleteById(id);
    }
}
