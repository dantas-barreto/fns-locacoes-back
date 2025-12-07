package com.fns.fns_locacoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fns.fns_locacoes.model.Veiculo;
import com.fns.fns_locacoes.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo) {
        return veiculoService.salvar(veiculo);
    }

    @GetMapping("/{id}")
    public Veiculo buscar(@PathVariable Long id) {
        return veiculoService.buscarPorId(id);
    }

    @GetMapping
    public List<Veiculo> listar() {
        return veiculoService.listar();
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        return veiculoService.atualizar(id, veiculo);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        veiculoService.remover(id);
    }
}
