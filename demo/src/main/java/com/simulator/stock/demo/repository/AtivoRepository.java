package com.simulator.stock.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.AtivoEntity;

public interface AtivoRepository extends JpaRepository<AtivoEntity, Long> {
    Optional<AtivoEntity> findByTicker(String ticker);

    List<AtivoEntity> findBySetor(String setor);

    Optional<AtivoEntity> findByNomeEmpresa(String nomeEmpresa);
}
