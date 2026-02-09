package com.simulator.stock.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulator.stock.demo.dto.TransacaoRequestDTO;
import com.simulator.stock.demo.dto.TransacaoResponseDTO;
import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.model.CarteiraEntity;
import com.simulator.stock.demo.model.PosicaoEntity;
import com.simulator.stock.demo.model.TransacaoEntity;
import com.simulator.stock.demo.model.UsuarioEntity;
import com.simulator.stock.demo.repository.AtivoRepository;
import com.simulator.stock.demo.repository.CarteiraRepository;
import com.simulator.stock.demo.repository.PosicaoRepository;
import com.simulator.stock.demo.repository.TransacaoRepository;
import com.simulator.stock.demo.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

     @Autowired
    private TransacaoRepository transacaoRepository;
    
    @Autowired
    private CarteiraRepository carteiraRepository;
    
    @Autowired
    private AtivoRepository ativoRepository;
    
    @Autowired
    private PosicaoRepository posicaoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TransacaoEntity> getTransacoesByCarteiraId(Long carteiraId) {
        return transacaoRepository.findByCarteiraId(carteiraId);
    }

    @Transactional
    public TransacaoEntity executarTransacao(Long carteiraId, TransacaoRequestDTO dto) {
        CarteiraEntity carteira = carteiraRepository.findById(carteiraId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        AtivoEntity ativo = ativoRepository.findById(dto.ativoId())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        BigDecimal valorUnitario = dto.valorUnitario();

        if (dto.valorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço unitário deve ser maior que zero");
}
        if (dto.tipo() == TransacaoEntity.TipoTransacao.COMPRA) {
            return executarCompra(carteira, ativo, dto, valorUnitario);
        } else if (dto.tipo() == TransacaoEntity.TipoTransacao.VENDA) {
            return executarVenda(carteira, ativo, dto, valorUnitario);
        } else {
            throw new RuntimeException("Tipo de transação inválido");
        }
    }

    private TransacaoEntity executarCompra(CarteiraEntity carteira, AtivoEntity ativo, TransacaoRequestDTO dto, BigDecimal valorUnitario) {
        BigDecimal valorTotal = valorUnitario.multiply(BigDecimal.valueOf(dto.quantidade()));
        UsuarioEntity usuario = carteira.getUsuario();

        if (usuario.getSaldoDisponivel().compareTo(valorTotal) < 0) {
            throw new RuntimeException("Saldo insuficiente para realizar a compra");
        }
        usuario.setSaldoDisponivel(usuario.getSaldoDisponivel().subtract(valorTotal));
        usuarioRepository.save(usuario);

        PosicaoEntity posicao = posicaoRepository.findByCarteiraAndAtivo(carteira, ativo)
                .orElse(new PosicaoEntity());
        
        if(posicao.getId() == null) {
            posicao.setCarteira(carteira);
            posicao.setAtivo(ativo);
            posicao.setQuantidade(dto.quantidade());
            posicao.setPrecoMedio(valorUnitario);
        } else {
            int quantidadeTotal = posicao.getQuantidade() + dto.quantidade();
            BigDecimal valorTotalAtual = posicao.getPrecoMedio().multiply(new BigDecimal(posicao.getQuantidade()));
            BigDecimal valorTotalCompra = valorUnitario.multiply(new BigDecimal(dto.quantidade()));
            BigDecimal novoValorTotal = valorTotalAtual.add(valorTotalCompra);
            BigDecimal novoPrecoMedio = novoValorTotal.divide(new BigDecimal(quantidadeTotal), 2, RoundingMode.HALF_UP);
            posicao.setQuantidade(quantidadeTotal);
            posicao.setPrecoMedio(novoPrecoMedio);
        }
        posicaoRepository.save(posicao);

        carteira.setTotalInvestido(carteira.getTotalInvestido().add(valorTotal));
        carteiraRepository.save(carteira);

        TransacaoEntity transacao = new TransacaoEntity(
            TransacaoEntity.TipoTransacao.COMPRA,
            dto.quantidade(),
            valorUnitario,
            carteira,
            ativo
        );

        return transacaoRepository.save(transacao);
    }

    private TransacaoEntity executarVenda(CarteiraEntity carteira, AtivoEntity ativo, TransacaoRequestDTO dto, BigDecimal valorUnitario) {
        
        BigDecimal valorTotal = valorUnitario.multiply(BigDecimal.valueOf(dto.quantidade()));
        UsuarioEntity usuario = carteira.getUsuario();
        PosicaoEntity posicao = posicaoRepository.findByCarteiraAndAtivo(carteira, ativo)
                .orElseThrow(() -> new RuntimeException("Posição não encontrada para venda"));
        BigDecimal valorInvestido = posicao.getPrecoMedio().multiply(BigDecimal.valueOf(dto.quantidade()));

        if (posicao.getQuantidade() < dto.quantidade()) {
            throw new RuntimeException("Quantidade insuficiente para realizar a venda");
        }

        posicao.setQuantidade(posicao.getQuantidade() - dto.quantidade());

        if (posicao.getQuantidade() == 0) {
            posicaoRepository.delete(posicao);
        } else {
            posicaoRepository.save(posicao);
        }

        usuario.setSaldoDisponivel(usuario.getSaldoDisponivel().add(valorTotal));
        usuarioRepository.save(usuario);

        carteira.setTotalInvestido(carteira.getTotalInvestido().subtract(valorInvestido));
        carteiraRepository.save(carteira);
        
        TransacaoEntity transacao = new TransacaoEntity(
            TransacaoEntity.TipoTransacao.VENDA,
            dto.quantidade(),
            valorUnitario,
            carteira,
            ativo
        );
        return transacaoRepository.save(transacao);
    }
    
    public TransacaoResponseDTO toResponseDTO(TransacaoEntity transacao) {
        return new TransacaoResponseDTO(
            transacao.getId(),
            transacao.getTipo(),
            transacao.getDataHora(),
            transacao.getAtivo().getId(),
            transacao.getAtivo().getTicker(),
            transacao.getQuantidade(),
            transacao.getValorUnitario(),
            transacao.getValorTotal(),
            transacao.getCarteira().getId()
        );
    }
}
