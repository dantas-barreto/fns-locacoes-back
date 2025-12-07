package com.fns.fns_locacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fns.fns_locacoes.model.Usuario;
import com.fns.fns_locacoes.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        validarUsuario(usuarioAtualizado);
        
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        existente.setNome(usuarioAtualizado.getNome());
        existente.setLogin(usuarioAtualizado.getLogin());
        existente.setSenhaHash(usuarioAtualizado.getSenhaHash());
        existente.setPerfilUsuario(usuarioAtualizado.getPerfilUsuario());

        return usuarioRepository.save(existente);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método de validação
    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do usuário é obrigatório");
        }
        if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            throw new RuntimeException("Login é obrigatório");
        }
        if (usuario.getSenhaHash() == null || usuario.getSenhaHash().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }
        if (usuario.getPerfilUsuario() == null) {
            throw new RuntimeException("Perfil de usuário é obrigatório");
        }
    }
}