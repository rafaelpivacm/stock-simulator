package com.simulator.stock.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.stock.demo.dto.PosicaoResponseDTO;
import com.simulator.stock.demo.model.PosicaoEntity;
import com.simulator.stock.demo.service.CarteiraService;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @GetMapping("/{carteiraId}/posicoes")
    public ResponseEntity<List<PosicaoResponseDTO>> getPosicoes(@PathVariable Long carteiraId) {
        try {
            List<PosicaoEntity> posicoes = carteiraService.getPosicoesByCarteiraId(carteiraId);
            List<PosicaoResponseDTO> response = posicoes.stream()
                .map(carteiraService::toPosicaoResponseDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

