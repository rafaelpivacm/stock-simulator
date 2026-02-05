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
import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.service.AtivoService;

import jakarta.validation.Valid;





@RestController
@RequestMapping("/ativos")
public class AtivoController {

    @Autowired
    private AtivoService ativoService;
    
    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<AtivoEntity> getByTicker(@PathVariable String ticker) {
        try {
                AtivoEntity ativo = ativoService.getByTicker(ticker);
                return ResponseEntity.ok(ativo);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

    @GetMapping("/empresa/{nomeEmpresa}")
    public ResponseEntity<AtivoEntity> getByNomeEmpresa(@PathVariable String nomeEmpresa) {
        try {
            AtivoEntity ativo = ativoService.getByEmpresa(nomeEmpresa);
            return ResponseEntity.ok(ativo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/setor/{setor}")
    public List<AtivoEntity> getBySetor(@PathVariable String setor) {
        return ativoService.getBySetor(setor);   
    }

    @GetMapping()
    public ResponseEntity<List<AtivoEntity>> getAll() {
        List<AtivoEntity> ativos = ativoService.getAllAtivos();
        return ResponseEntity.ok(ativos);
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
    public ResponseEntity<AtivoEntity> atualizarAtivo(@PathVariable Long id, @RequestBody AtivoEntity ativoAtualizado) {
        try {
            AtivoEntity ativo = ativoService.atualizarAtivo(id, ativoAtualizado);
            return ResponseEntity.ok(ativo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
  
    @PostMapping()
    public ResponseEntity<AtivoEntity> criarAtivo(@Valid @RequestBody AtivoRequestDTO dto) {
        try {
            AtivoEntity novoAtivo = ativoService.criarAtivo(dto);
            return ResponseEntity.created(URI.create("/ativos/" + novoAtivo.getId())).body(novoAtivo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
