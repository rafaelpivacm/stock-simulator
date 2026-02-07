package com.simulator.stock.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.PosicaoEntity;

public interface PosicaoRepository extends JpaRepository<PosicaoEntity, Long> {

}
