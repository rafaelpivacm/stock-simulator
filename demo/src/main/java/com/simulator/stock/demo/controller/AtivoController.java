package com.simulator.stock.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.stock.demo.dto.AtivoRequestDTO;
import com.simulator.stock.demo.dto.AtivoResponseDTO;
import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.service.AtivoService;

import jakarta.validation.Valid;





@RestController
@RequestMapping("/ativos")
public class AtivoController {

    @Autowired
    private AtivoService ativoService;
    
    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<AtivoResponseDTO> getByTicker(@PathVariable String ticker) {
        try {
                AtivoEntity ativo = ativoService.getByTicker(ticker);
                AtivoResponseDTO dto = ativoService.toResponseDTO(ativo);
                return ResponseEntity.ok(dto);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

    @GetMapping("/empresa/{nomeEmpresa}")
    public ResponseEntity<AtivoResponseDTO> getByNomeEmpresa(@PathVariable String nomeEmpresa) {
        try {
            AtivoEntity ativo = ativoService.getByEmpresa(nomeEmpresa);
            AtivoResponseDTO dto = ativoService.toResponseDTO(ativo);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<AtivoResponseDTO>> getBySetor(@PathVariable String setor) {
        List<AtivoEntity> ativos = ativoService.getBySetor(setor);
        List<AtivoResponseDTO> dtos = ativos.stream()
            .map(ativoService::toResponseDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping()
    public ResponseEntity<List<AtivoResponseDTO>> getAll() {
        List<AtivoEntity> ativos = ativoService.getAllAtivos();
        List<AtivoResponseDTO> dtos = ativos.stream()
            .map(ativoService::toResponseDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtivo(@PathVariable Long id) {
        try {
            ativoService.deletarAtivo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivoResponseDTO> atualizarAtivo(@PathVariable Long id, @RequestBody AtivoEntity ativoAtualizado) {
        try {
            AtivoEntity ativo = ativoService.atualizarAtivo(id, ativoAtualizado);
            AtivoResponseDTO dto = ativoService.toResponseDTO(ativo);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
  
    @PostMapping()
    public ResponseEntity<AtivoResponseDTO> criarAtivo(@Valid @RequestBody AtivoRequestDTO dto) {
        try {
            AtivoEntity novoAtivo = ativoService.criarAtivo(dto);
            AtivoResponseDTO responseDTO = ativoService.toResponseDTO(novoAtivo);
            return ResponseEntity.created(URI.create("/ativos/" + novoAtivo.getId())).body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
