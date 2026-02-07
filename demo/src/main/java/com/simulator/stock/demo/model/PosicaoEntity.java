package com.simulator.stock.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "posicao")
public class PosicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0")
    private Integer quantidade;

    @DecimalMin("0")
    private BigDecimal precoMedio;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private AtivoEntity ativo;

    @ManyToOne
    @JoinColumn(name = "carteira_id")
    private CarteiraEntity carteira;

    public PosicaoEntity() {
    }

    public PosicaoEntity(Integer quantidade, BigDecimal precoMedio, AtivoEntity ativo, CarteiraEntity carteira) {
        this.quantidade = quantidade;
        this.precoMedio = precoMedio;
        this.ativo = ativo;
        this.carteira = carteira;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(BigDecimal precoMedio) {
        this.precoMedio = precoMedio;
    }

    public AtivoEntity getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoEntity ativo) {
        this.ativo = ativo;
    }

    public CarteiraEntity getCarteira() {
        return carteira;
    }

    public void setCarteira(CarteiraEntity carteira) {
        this.carteira = carteira;
    }

    

}
