package com.simulator.stock.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.stock.demo.dto.TransacaoRequestDTO;
import com.simulator.stock.demo.dto.TransacaoResponseDTO;
import com.simulator.stock.demo.model.TransacaoEntity;
import com.simulator.stock.demo.service.TransacaoService;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/carteira/{carteiraId}")
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(
            @PathVariable Long carteiraId,
            @RequestBody TransacaoRequestDTO dto) {
        try {
            TransacaoEntity transacao = transacaoService.executarTransacao(carteiraId, dto);
            TransacaoResponseDTO response = transacaoService.toResponseDTO(transacao);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/carteira/{carteiraId}")
    public ResponseEntity<List<TransacaoResponseDTO>> getTransacoesByCarteira(@PathVariable Long carteiraId) {
        try {
            List<TransacaoEntity> transacoes = transacaoService.getTransacoesByCarteiraId(carteiraId);
            List<TransacaoResponseDTO> response = transacoes.stream()
                    .map(transacaoService::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

