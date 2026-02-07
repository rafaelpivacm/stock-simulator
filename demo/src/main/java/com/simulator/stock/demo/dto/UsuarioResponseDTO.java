package com.simulator.stock.demo.dto;

import java.math.BigDecimal;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    BigDecimal saldoDisponivel
) {
    public UsuarioResponseDTO {
        nome = nome != null ? nome.trim() : nome;
        email = email != null ? email.trim().toLowerCase() : email;
    }

}
