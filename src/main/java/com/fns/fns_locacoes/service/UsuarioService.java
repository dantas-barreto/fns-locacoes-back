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
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        existente.setNome(usuarioAtualizado.getnome());
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
}
