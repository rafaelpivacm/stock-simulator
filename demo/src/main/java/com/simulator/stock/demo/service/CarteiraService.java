package com.simulator.stock.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulator.stock.demo.dto.PosicaoResponseDTO;
import com.simulator.stock.demo.model.CarteiraEntity;
import com.simulator.stock.demo.model.PosicaoEntity;
import com.simulator.stock.demo.repository.CarteiraRepository;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    public CarteiraEntity getCarteiraById(Long id) {
        return carteiraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
    }

    public List<PosicaoEntity> getPosicoesByCarteiraId(Long carteiraId) {
    CarteiraEntity carteira = carteiraRepository.findById(carteiraId)
            .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
    return carteira.getPosicoes();
    }

    public PosicaoResponseDTO toPosicaoResponseDTO(PosicaoEntity posicao) {
        BigDecimal valorInvestido = posicao.getPrecoMedio()
                .multiply(BigDecimal.valueOf(posicao.getQuantidade()));

        return new PosicaoResponseDTO(
            posicao.getId(),
            posicao.getAtivo().getTicker(),
            posicao.getAtivo().getNomeEmpresa(),
            posicao.getQuantidade(),
            posicao.getPrecoMedio(),
            valorInvestido,
            BigDecimal.ZERO,
            BigDecimal.ZERO
        );
    }
}
