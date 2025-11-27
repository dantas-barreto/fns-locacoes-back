package com.fns.fns_locacoes.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Recibo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataEmissao;

    @OneToOne
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    public Long getId() {
        return id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Locacao getLocacao() {
        return locacao;
    }
}
