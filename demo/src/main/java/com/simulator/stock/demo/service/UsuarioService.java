package com.simulator.stock.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulator.stock.demo.dto.UsuarioRequestDTO;
import com.simulator.stock.demo.dto.UsuarioResponseDTO;
import com.simulator.stock.demo.model.CarteiraEntity;
import com.simulator.stock.demo.model.UsuarioEntity;
import com.simulator.stock.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioEntity getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UsuarioEntity getByEmail (String email) {
        return usuarioRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UsuarioEntity atualizarUsuario(Long usuarioId, UsuarioRequestDTO usuarioAtualizado) {
        UsuarioEntity usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(usuarioAtualizado.nome() != null && !usuarioAtualizado.nome().trim().isEmpty()) {
            usuarioExistente.setNome(usuarioAtualizado.nome().trim());
        }

         if(usuarioAtualizado.email() != null && !usuarioAtualizado.email().trim().isEmpty()) {
        String novoEmail = usuarioAtualizado.email().trim().toLowerCase();
        
        if (!novoEmail.equals(usuarioExistente.getEmail())) {

            if (usuarioRepository.findByEmail(novoEmail).isPresent()) {
                throw new RuntimeException("Email já utilizado: " + novoEmail);
            }
            usuarioExistente.setEmail(novoEmail);
        }
    }

        if(usuarioAtualizado.senha() != null && !usuarioAtualizado.senha().trim().isEmpty()) {
            usuarioExistente.setSenha(usuarioAtualizado.senha().trim());
        }

        if(usuarioAtualizado.saldoDisponivel() != null && 
           usuarioAtualizado.saldoDisponivel().compareTo(BigDecimal.ZERO) >= 0) {
            usuarioExistente.setSaldoDisponivel(usuarioAtualizado.saldoDisponivel());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public UsuarioEntity criarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email().toLowerCase()).isPresent()) {
            throw new RuntimeException("Email já existe: " + dto.email());
        }
        UsuarioEntity novoUsuario = new UsuarioEntity();
        novoUsuario.setEmail(dto.email().toLowerCase());
        novoUsuario.setNome(dto.nome());
        novoUsuario.setSenha(dto.senha());
        if(dto.saldoDisponivel() != null && dto.saldoDisponivel().compareTo(BigDecimal.ZERO) >= 0) {
            novoUsuario.setSaldoDisponivel(dto.saldoDisponivel());
        }
        CarteiraEntity carteira = new CarteiraEntity();
        carteira.setUsuario(novoUsuario);
        carteira.setTotalInvestido(BigDecimal.ZERO);
        novoUsuario.setCarteira(carteira);
        return usuarioRepository.save(novoUsuario);
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDTO toUsuarioResponseDTO(UsuarioEntity usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSaldoDisponivel()
        );
    }

}