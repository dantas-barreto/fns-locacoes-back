package com.fns.fns_locacoes.controller;

import com.fns.fns_locacoes.model.Locacao;
import com.fns.fns_locacoes.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping
    public Locacao criar(@RequestBody Locacao locacao) {
        return locacaoService.salvar(locacao);
    }

    @GetMapping("/{id}")
    public Locacao buscar(@PathVariable Long id) {
        return locacaoService.buscarPorId(id);
    }

    @GetMapping
    public List<Locacao> listar() {
        return locacaoService.listar();
    }

    @PutMapping("/{id}")
    public Locacao atualizar(@PathVariable Long id, @RequestBody Locacao locacao) {
        return locacaoService.atualizar(id, locacao);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        locacaoService.remover(id);
    }
}
