package com.simulator.stock.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "transacao")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoTransacao tipo;
    @Min(1)
    @NotNull
    private Integer quantidade;
    @DecimalMin("0.01")
    @NotNull
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    @NotNull
    private LocalDateTime dataHora;
    @ManyToOne
    @JoinColumn(name = "carteira_id")
    private CarteiraEntity carteira;
    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private AtivoEntity ativo;

    public TransacaoEntity() {
        this.dataHora = LocalDateTime.now();
    }

    public TransacaoEntity(TipoTransacao tipo, Integer quantidade, BigDecimal valorUnitario, CarteiraEntity carteira, AtivoEntity ativo) {
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataHora = LocalDateTime.now();
        this.carteira = carteira;
        this.ativo = ativo;
        calcularValorTotal();
    }

    public Long getId() {
        return id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularValorTotal();
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularValorTotal();
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public CarteiraEntity getCarteira() {
        return carteira;
    }

    public void setCarteira(CarteiraEntity carteira) {
        this.carteira = carteira;
    }

    public AtivoEntity getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoEntity ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public enum TipoTransacao {
        COMPRA, VENDA
    }

    private void calcularValorTotal() {
        if (valorUnitario != null && quantidade != null) {
            this.valorTotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
        } else {
            this.valorTotal = BigDecimal.ZERO;
        }
    }
}
