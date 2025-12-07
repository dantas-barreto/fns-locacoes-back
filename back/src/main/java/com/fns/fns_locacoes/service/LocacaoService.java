package com.fns.fns_locacoes.service;

import com.fns.fns_locacoes.model.Cliente;
import com.fns.fns_locacoes.model.Locacao;
import com.fns.fns_locacoes.model.Usuario;
import com.fns.fns_locacoes.model.Veiculo;
import com.fns.fns_locacoes.repository.ClienteRepository;
import com.fns.fns_locacoes.repository.LocacaoRepository;
import com.fns.fns_locacoes.repository.UsuarioRepository;
import com.fns.fns_locacoes.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;

    public LocacaoService(
            LocacaoRepository locacaoRepository,
            ClienteRepository clienteRepository,
            UsuarioRepository usuarioRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.locacaoRepository = locacaoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Locacao salvar(Locacao locacao) {
        validarLocacao(locacao);
        
        Cliente cliente = clienteRepository.findById(locacao.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        locacao.setCliente(cliente);

        Usuario usuario = usuarioRepository.findById(locacao.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        locacao.setUsuario(usuario);

        Veiculo veiculo = veiculoRepository.findById(locacao.getVeiculo().getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        locacao.setVeiculo(veiculo);

        // Calcular valor final automaticamente
        calcularValorFinal(locacao);

        return locacaoRepository.save(locacao);
    }

    public Locacao atualizar(Long id, Locacao locacaoAtualizada) {
        Locacao existente = locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada"));

        validarLocacao(locacaoAtualizada);

        existente.setDataInicio(locacaoAtualizada.getDataInicio());
        existente.setDataFimPrevista(locacaoAtualizada.getDataFimPrevista());
        existente.setDataDevolucao(locacaoAtualizada.getDataDevolucao());
        existente.setValorDiaria(locacaoAtualizada.getValorDiaria());

        Cliente cliente = clienteRepository.findById(locacaoAtualizada.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        existente.setCliente(cliente);

        Usuario usuario = usuarioRepository.findById(locacaoAtualizada.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existente.setUsuario(usuario);

        Veiculo veiculo = veiculoRepository.findById(locacaoAtualizada.getVeiculo().getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        existente.setVeiculo(veiculo);

        // Recalcular valor final
        calcularValorFinal(existente);

        return locacaoRepository.save(existente);
    }

    public Locacao buscarPorId(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
    }

    public List<Locacao> listar() {
        return locacaoRepository.findAll();
    }

    public void remover(Long id) {
        locacaoRepository.deleteById(id);
    }

    // Método de validação
    private void validarLocacao(Locacao locacao) {
        if (locacao.getDataInicio() == null) {
            throw new RuntimeException("Data de início é obrigatória");
        }
        if (locacao.getDataFimPrevista() == null) {
            throw new RuntimeException("Data fim prevista é obrigatória");
        }
        if (locacao.getValorDiaria() == null || locacao.getValorDiaria() <= 0) {
            throw new RuntimeException("Valor diária deve ser positivo");
        }
        if (locacao.getCliente() == null || locacao.getCliente().getId() == null) {
            throw new RuntimeException("Cliente é obrigatório");
        }
        if (locacao.getUsuario() == null || locacao.getUsuario().getId() == null) {
            throw new RuntimeException("Usuário é obrigatório");
        }
        if (locacao.getVeiculo() == null || locacao.getVeiculo().getId() == null) {
            throw new RuntimeException("Veículo é obrigatório");
        }
        if (locacao.getDataInicio().after(locacao.getDataFimPrevista())) {
            throw new RuntimeException("Data de início não pode ser após data de fim prevista");
        }
    }

    // Método para calcular valor final automaticamente
    private void calcularValorFinal(Locacao locacao) {
        Date dataFim = locacao.getDataDevolucao() != null ? locacao.getDataDevolucao() : locacao.getDataFimPrevista();
        long diffInMillies = Math.abs(dataFim.getTime() - locacao.getDataInicio().getTime());
        long dias = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1; // +1 para incluir o dia inicial
        double valorFinal = dias * locacao.getValorDiaria();
        locacao.setValorFinal(valorFinal);
    }
}
