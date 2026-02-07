package com.simulator.stock.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.stock.demo.dto.UsuarioRequestDTO;
import com.simulator.stock.demo.dto.UsuarioResponseDTO;
import com.simulator.stock.demo.model.UsuarioEntity;
import com.simulator.stock.demo.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        try {
            UsuarioEntity usuario = usuarioService.getById(id);
            UsuarioResponseDTO dto = usuarioService.toUsuarioResponseDTO(usuario);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> getByEmail(@PathVariable String email) {
        try {
            UsuarioEntity usuario = usuarioService.getByEmail(email);
            UsuarioResponseDTO dto = usuarioService.toUsuarioResponseDTO(usuario);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO dto) {
        try {
            UsuarioEntity novoUsuario = usuarioService.criarUsuario(dto);
            UsuarioResponseDTO responseDTO = usuarioService.toUsuarioResponseDTO(novoUsuario);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        try {
            UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
            UsuarioResponseDTO responseDTO = usuarioService.toUsuarioResponseDTO(usuarioAtualizado);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("Usuário não encontrado")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
