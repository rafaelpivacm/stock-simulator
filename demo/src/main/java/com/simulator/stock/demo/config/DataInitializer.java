package com.simulator.stock.demo.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.repository.AtivoRepository;


@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(AtivoRepository repository) {
        return args -> {
        
            if (repository.count() == 0) {
                System.out.println("Populando banco com dados iniciais...");
                
                AtivoEntity petr4 = criarAtivo("PETR4", "Petrobras", "Energia", "35.50");
                AtivoEntity vale3 = criarAtivo("VALE3", "Vale S.A.", "Mineração", "68.90");
                AtivoEntity itub4 = criarAtivo("ITUB4", "Itaú Unibanco", "Financeiro", "32.15");
                AtivoEntity bbdc4 = criarAtivo("BBDC4", "Bradesco", "Financeiro", "16.78");
                AtivoEntity abev3 = criarAtivo("ABEV3", "Ambev", "Bebidas", "14.20");
                AtivoEntity mglu3 = criarAtivo("MGLU3", "Magazine Luiza", "Varejo", "2.45");
                AtivoEntity wege3 = criarAtivo("WEGE3", "WEG", "Indústria", "25.80");
                AtivoEntity bova11 = criarAtivo("BOVA11", "ETF Ibovespa", "ETF", "120.00");
                
                repository.saveAll(Arrays.asList(petr4, vale3, itub4, bbdc4, 
                                                 abev3, mglu3, wege3, bova11));
                
                System.out.println(repository.count() + " ativos criados!");
            }
        };
    }
    
    private AtivoEntity criarAtivo(String ticker, String nome, String setor, String preco) {
        AtivoEntity ativo = new AtivoEntity();
        ativo.setTicker(ticker);
        ativo.setNomeEmpresa(nome);
        ativo.setSetor(setor);
        ativo.setPrecoAtual(new BigDecimal(preco));
        ativo.setDataUltimaAtualizacao(LocalDateTime.now());
        return ativo;
    }
}