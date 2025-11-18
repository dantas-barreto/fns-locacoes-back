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

import com.fns.fns_locacoes.model.Recibo;
import com.fns.fns_locacoes.service.ReciboService;

@RestController
@RequestMapping("/recibos")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @PostMapping
    public Recibo criar(@RequestBody Recibo recibo) {
        return reciboService.salvar(recibo);
    }

    @GetMapping("/{id}")
    public Recibo buscar(@PathVariable Long id) {
        return reciboService.buscarPorId(id);
    }

    @GetMapping
    public List<Recibo> listar() {
        return reciboService.listar();
    }

    @PutMapping("/{id}")
    public Recibo atualizar(@PathVariable Long id, @RequestBody Recibo recibo) {
        return reciboService.atualizar(id, recibo);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        reciboService.remover(id);
    }
}
