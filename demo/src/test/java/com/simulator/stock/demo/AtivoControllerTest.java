package com.simulator.stock.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.repository.AtivoRepository;
import com.simulator.stock.demo.service.AtivoService;

@SpringBootTest
public class AtivoControllerTest {
    
    @Autowired
    private AtivoRepository ativoRepository;
    
    @Autowired
    private AtivoService ativoService;

    @Test
    void testAtualizarAtivoParcial() {
        // 1. ARRANGE: Cria um ativo para testar
        AtivoEntity ativoOriginal = new AtivoEntity();
        ativoOriginal.setTicker("TEST3");
        ativoOriginal.setNomeEmpresa("Empresa Teste");
        ativoOriginal.setSetor("Setor Antigo");
        ativoOriginal.setPrecoAtual(new BigDecimal("100.00"));
        AtivoEntity salvo = ativoRepository.save(ativoOriginal);
        
        // 2. ACT: Cria dados de atualização (apenas setor)
        AtivoEntity atualizacao = new AtivoEntity();
        atualizacao.setSetor("Novo Setor");
        
        // Executa a atualização
        AtivoEntity atualizado = ativoService.atualizarAtivo(salvo.getId(), atualizacao);
        
        // 3. ASSERT: Verifica resultados
        assertNotNull(atualizado);
        assertEquals("Novo Setor", atualizado.getSetor()); // Campo alterado
        assertEquals("Empresa Teste", atualizado.getNomeEmpresa()); // Não alterado
        assertEquals("TEST3", atualizado.getTicker()); // Não alterado
        assertEquals(0, new BigDecimal("100.00").compareTo(atualizado.getPrecoAtual())); // Não alterado
    }
}
