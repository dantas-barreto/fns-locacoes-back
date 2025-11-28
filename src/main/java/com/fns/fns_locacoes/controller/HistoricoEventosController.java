package com.fns.fns_locacoes.controller;

import com.fns.fns_locacoes.model.HistoricoEventos;
import com.fns.fns_locacoes.service.HistoricoEventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HistoricoEventos")
public class HistoricoEventosController {

    @Autowired
    private HistoricoEventosService historicoEventosService;

    @PostMapping
    public HistoricoEventos criar(@RequestBody HistoricoEventos historicoEventos) {
        return historicoEventosService.salvar(historicoEventos);
    }

    @GetMapping("/{id}")
    public HistoricoEventos buscar(@PathVariable Long id) {
        return historicoEventosService.buscarPorId(id);
    }

    @GetMapping
    public List<HistoricoEventos> listar() {
        return historicoEventosService.listar();
    }

    @PutMapping("/{id}")
    public HistoricoEventos atualizar(@PathVariable Long id, @RequestBody HistoricoEventos historicoEventos) {
        return historicoEventosService.atualizar(id, historicoEventos);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        historicoEventosService.remover(id);
    }
}
