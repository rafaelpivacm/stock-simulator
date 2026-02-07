package com.simulator.stock.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carteira")
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "carteira")
    private List<PosicaoEntity> posicoes = new ArrayList<>();

    private BigDecimal totalInvestido;

    public CarteiraEntity() {
        this.totalInvestido = BigDecimal.ZERO;
    }

    public CarteiraEntity(UsuarioEntity usuario, List<PosicaoEntity> posicoes, BigDecimal totalInvestido) {
        this.usuario = usuario;
        this.posicoes = posicoes;
        this.totalInvestido = totalInvestido;
    }

    public Long getId() {
        return id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<PosicaoEntity> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(List<PosicaoEntity> posicoes) {
        this.posicoes = posicoes    ;
    }

    public BigDecimal getTotalInvestido() {
        return totalInvestido;
    }

    public void setTotalInvestido(BigDecimal totalInvestido) {
        this.totalInvestido = totalInvestido;
    }
}
