package com.simulator.stock.demo.dto;

import java.math.BigDecimal;

import com.simulator.stock.demo.model.TransacaoEntity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(
    @NotNull
    TransacaoEntity.TipoTransacao tipo,
    @NotNull
    Long ativoId,
    @NotNull
    @Min(1)
    Integer quantidade,
    @NotNull
    @DecimalMin("0.01")
    BigDecimal valorUnitario
) {

}
