package com.simulator.stock.demo.dto;

import java.math.BigDecimal;

public record PosicaoResponseDTO(
    Long ativoId,
    String ativoTicker,
    String ativoNome,
    Integer quantidade,
    BigDecimal precoMedio,
    BigDecimal valorInvestido,
    BigDecimal valorAtual,
    BigDecimal ganhoPrejuizo
) {

}
