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

import java.util.List;

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
        Cliente cliente = clienteRepository.findById(locacao.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        locacao.setCliente(cliente);

        Usuario usuario = usuarioRepository.findById(locacao.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        locacao.setUsuario(usuario);

        Veiculo veiculo = veiculoRepository.findById(locacao.getVeiculo().getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        locacao.setVeiculo(veiculo);

        return locacaoRepository.save(locacao);
    }

    public Locacao atualizar(Long id, Locacao locacaoAtualizada) {
        Locacao existente = locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada"));

        existente.setDataInicio(locacaoAtualizada.getDataInicio());
        existente.setDataFimPrevista(locacaoAtualizada.getDataFimPrevista());
        existente.setDataDevolucao(locacaoAtualizada.getDataDevolucao());
        existente.setValorDiaria(locacaoAtualizada.getValorDiaria());
        existente.setValorFinal(locacaoAtualizada.getValorFinal());
        existente.setUsuarioResposavelId(locacaoAtualizada.getUsuarioResposavelId());

        Cliente cliente = clienteRepository.findById(locacaoAtualizada.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        existente.setCliente(cliente);

        Usuario usuario = usuarioRepository.findById(locacaoAtualizada.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existente.setUsuario(usuario);

        Veiculo veiculo = veiculoRepository.findById(locacaoAtualizada.getVeiculo().getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        existente.setVeiculo(veiculo);

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
}
