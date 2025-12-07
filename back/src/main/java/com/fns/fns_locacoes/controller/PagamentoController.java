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
import com.fns.fns_locacoes.model.Pagamento;
import com.fns.fns_locacoes.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public Pagamento criar(@RequestBody Pagamento pagamento) {
        return pagamentoService.salvar(pagamento);
    }

    @GetMapping("/{id}")
    public Pagamento buscar(@PathVariable Long id) {
        return pagamentoService.buscarPorId(id);
    }

    @GetMapping
    public List<Pagamento> listar() {
        return pagamentoService.listar();
    }

    @PutMapping("/{id}")
    public Pagamento atualizar(@PathVariable Long id, @RequestBody Pagamento pagamento) {
        return pagamentoService.atualizar(id, pagamento);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        pagamentoService.remover(id);
    }
}
