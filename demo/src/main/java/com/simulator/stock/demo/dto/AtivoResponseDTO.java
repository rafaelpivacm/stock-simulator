package com.simulator.stock.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtivoResponseDTO(
    Long id,
    String ticker,
    String nomeEmpresa,
    String setor,
    BigDecimal precoAtual,
    LocalDateTime dataUltimaAtualizacao
) {

}
