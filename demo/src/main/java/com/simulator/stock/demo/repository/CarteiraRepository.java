package com.simulator.stock.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.CarteiraEntity;

public interface CarteiraRepository extends JpaRepository<CarteiraEntity, Long> {

}
