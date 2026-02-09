package com.simulator.stock.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.simulator.stock.demo.model.TransacaoEntity;

public record TransacaoResponseDTO(
    Long id,
    TransacaoEntity.TipoTransacao tipo,
    LocalDateTime dataHora,
    Long ativoId,
    String ativoTicker,
    Integer quantidade,
    BigDecimal valorUnitario,
    BigDecimal valorTotal,
    Long carteiraId
) {

}
