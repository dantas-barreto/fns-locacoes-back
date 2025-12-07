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
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        validarCliente(clienteAtualizado);
        
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

    // Método de validação
    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do cliente é obrigatório");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new RuntimeException("CPF do cliente é obrigatório");
        }
        if (!validarCPF(cliente.getCpf())) {
            throw new RuntimeException("CPF inválido");
        }
    }

    // Validação básica de CPF
    private boolean validarCPF(String cpf) {
        // Remove caracteres especiais
        cpf = cpf.replaceAll("\\D", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        return true;
    }
}