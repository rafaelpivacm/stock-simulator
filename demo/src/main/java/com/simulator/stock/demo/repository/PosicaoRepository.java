package com.simulator.stock.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.model.CarteiraEntity;
import com.simulator.stock.demo.model.PosicaoEntity;

public interface PosicaoRepository extends JpaRepository<PosicaoEntity, Long> {
    Optional<PosicaoEntity> findByCarteiraAndAtivo(CarteiraEntity carteira, AtivoEntity ativo);

}
