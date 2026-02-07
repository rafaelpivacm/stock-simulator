package com.simulator.stock.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "senha", nullable = false)
    private String senha;

    @DecimalMin("0")
    @Column(name = "saldoDisponivel", nullable = false)
    private BigDecimal saldoDisponivel;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CarteiraEntity carteira;

    public UsuarioEntity() {
        this.saldoDisponivel = new BigDecimal("100000.00");
    }

    public UsuarioEntity(String nome, String email, String senha, BigDecimal saldoDisponivel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.saldoDisponivel = saldoDisponivel;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public CarteiraEntity getCarteira() {
        return carteira;
    }

    public void setCarteira(CarteiraEntity carteira) {
        this.carteira = carteira;
    }

}