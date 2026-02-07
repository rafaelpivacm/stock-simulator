package com.simulator.stock.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
    @NotBlank
    String nome,
    @NotBlank
    String email,
    @NotBlank
    String senha,
    @DecimalMin("0")
    BigDecimal saldoDisponivel
) {
    public UsuarioRequestDTO {
        nome = nome != null ? nome.trim() : nome;
        email = email != null ? email.trim().toLowerCase() : email;
        senha = senha != null ? senha.trim() : senha;
        saldoDisponivel = saldoDisponivel != null ? saldoDisponivel : BigDecimal.ZERO;
    }

}
