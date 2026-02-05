package com.simulator.stock.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "ativo")
public class AtivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da empresa é obrigatório")
    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @NotBlank(message = "O ticker é obrigatório")
    @Column(name = "ticker", nullable = false, unique= true)
    @Pattern(regexp = "^[A-Z]{4}[0-9]{1,2}$", message = "Formato: 4 letras + 1 ou 2 números. Ex: PETR4 ou BOVA11")
    private String ticker;

    @Column(name = "setor")
    private String setor;

    @Column(name = "preco_atual")
    private BigDecimal precoAtual;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;

    public AtivoEntity() {
    }

    public AtivoEntity(String nomeEmpresa, String ticker, String setor, BigDecimal precoAtual, LocalDateTime dataUltimaAtualizacao) {
        this.nomeEmpresa = nomeEmpresa;
        this.ticker = ticker;
        this.setor = setor;
        this.precoAtual = precoAtual;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public BigDecimal getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(BigDecimal precoAtual) {
        this.precoAtual = precoAtual;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    
}
