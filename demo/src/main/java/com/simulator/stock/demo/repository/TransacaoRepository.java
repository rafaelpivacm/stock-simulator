package com.simulator.stock.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.TransacaoEntity;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {

    List<TransacaoEntity> findByCarteiraId(Long carteiraId);
    List<TransacaoEntity> findByAtivoId(Long ativoId);
    
}
