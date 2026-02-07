package com.simulator.stock.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulator.stock.demo.model.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    public Optional<UsuarioEntity> findByEmail(String email);
}
