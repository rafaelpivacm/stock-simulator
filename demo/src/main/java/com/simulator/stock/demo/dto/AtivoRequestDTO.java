package com.simulator.stock.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AtivoRequestDTO(
    @NotBlank 
    @Pattern(
    regexp = "^[A-Z]{4}[0-9]{1,2}$", 
    message = "Formato inválido. Use 4 letras + 1-2 números. Ex: PETR4, BOVA11")
    String ticker, 

    @NotBlank 
    String nomeEmpresa, 

    String setor
) {
    public AtivoRequestDTO {
        ticker = ticker != null ? ticker.toUpperCase() : ticker;
        
        nomeEmpresa = nomeEmpresa != null ? nomeEmpresa.trim() : nomeEmpresa;
        
        setor = setor != null ? setor.trim() : null;
    }
}
