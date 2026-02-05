package com.simulator.stock.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulator.stock.demo.dto.AtivoRequestDTO;
import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.repository.AtivoRepository;

@Service
public class AtivoService {

    @Autowired
    private AtivoRepository ativoRepository;

    public AtivoEntity getByTicker(String ticker) {
        return ativoRepository.findByTicker(ticker.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
    }

    public List<AtivoEntity> getBySetor(String setor) {
        return ativoRepository.findBySetor(setor);
    }

    public List<AtivoEntity> getAllAtivos() {
        return ativoRepository.findAll();
    }

    public AtivoEntity getByEmpresa (String nomeEmpresa) {
        return ativoRepository.findByNomeEmpresa(nomeEmpresa)
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
    }

    public void deletarAtivo(Long id) {
        if (!ativoRepository.existsById(id)) {
            throw new RuntimeException("Ativo não encontrado");
        }
        ativoRepository.deleteById(id);
    }

    public AtivoEntity atualizarAtivo(Long id, AtivoEntity ativoAtualizado) {
        AtivoEntity ativoExistente = ativoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
        if(ativoAtualizado.getNomeEmpresa() != null && !ativoAtualizado.getNomeEmpresa().trim().isEmpty()) {
            ativoExistente.setNomeEmpresa(ativoAtualizado.getNomeEmpresa().trim());
        }
        if(ativoAtualizado.getSetor() != null && !ativoAtualizado.getSetor().trim().isEmpty()) {
            ativoExistente.setSetor(ativoAtualizado.getSetor().trim());
        }
        if(ativoAtualizado.getPrecoAtual() != null && ativoAtualizado.getPrecoAtual().compareTo(java.math.BigDecimal.ZERO) > 0) {
            ativoExistente.setPrecoAtual(ativoAtualizado.getPrecoAtual());
        }
        if(ativoAtualizado.getDataUltimaAtualizacao() != null) {
            ativoExistente.setDataUltimaAtualizacao(ativoAtualizado.getDataUltimaAtualizacao());
        }
        return ativoRepository.save(ativoExistente);
    }

    public AtivoEntity criarAtivo(AtivoRequestDTO dto) {
        if (ativoRepository.findByTicker(dto.ticker()).isPresent()) {
            throw new RuntimeException("Ticker já existe: " + dto.ticker());
        }
        AtivoEntity novoAtivo = new AtivoEntity();
        novoAtivo.setTicker(dto.ticker());
        novoAtivo.setNomeEmpresa(dto.nomeEmpresa());
        novoAtivo.setSetor(dto.setor());
        return ativoRepository.save(novoAtivo);
    }
    
}
