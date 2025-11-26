package com.fns.fns_locacoes.controller;

import com.fns.fns_locacoes.model.Mensagem;
import com.fns.fns_locacoes.service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public Mensagem criar(@RequestBody Mensagem mensagem) {
        return mensagemService.salvar(mensagem);
    }

    @GetMapping("/{id}")
    public Mensagem buscar(@PathVariable Long id) {
        return mensagemService.buscarPorId(id);
    }

    @GetMapping
    public List<Mensagem> listar() {
        return mensagemService.listar();
    }

    @PutMapping("/{id}")
    public Mensagem atualizar(@PathVariable Long id, @RequestBody Mensagem mensagem) {
        return mensagemService.atualizar(id, mensagem);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        mensagemService.remover(id);
    }
}
