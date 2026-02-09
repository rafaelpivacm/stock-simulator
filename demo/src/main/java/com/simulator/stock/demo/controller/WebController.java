package com.simulator.stock.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.simulator.stock.demo.model.AtivoEntity;
import com.simulator.stock.demo.model.CarteiraEntity;
import com.simulator.stock.demo.model.PosicaoEntity;
import com.simulator.stock.demo.model.TransacaoEntity;
import com.simulator.stock.demo.model.UsuarioEntity;
import com.simulator.stock.demo.service.AtivoService;
import com.simulator.stock.demo.service.CarteiraService;
import com.simulator.stock.demo.service.TransacaoService;
import com.simulator.stock.demo.service.UsuarioService;

@Controller
public class WebController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AtivoService ativoService;
    
    @Autowired
    private CarteiraService carteiraService;
    
    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/")
    public String home(Model model) {
        try {
  
            UsuarioEntity usuario = usuarioService.getById(1L);
            CarteiraEntity carteira = carteiraService.getCarteiraById(1L);
            
            List<AtivoEntity> ativos = ativoService.getAllAtivos();
            if (ativos.size() > 5) {
                ativos = ativos.subList(0, 5);
            }
            
            List<TransacaoEntity> transacoes = transacaoService.getTransacoesByCarteiraId(1L);
            if (transacoes.size() > 5) {
                transacoes = transacoes.subList(0, 5);
            }
            
            model.addAttribute("saldo", usuario.getSaldoDisponivel());
            model.addAttribute("posicoes", carteira.getPosicoes());
            model.addAttribute("transacoes", transacoes);
            model.addAttribute("ativos", ativos);
            model.addAttribute("pageTitle", "Dashboard");
            
        } catch (RuntimeException e) {
            model.addAttribute("saldo", 0);
            model.addAttribute("posicoes", new ArrayList<>());
            model.addAttribute("transacoes", new ArrayList<>());
            model.addAttribute("ativos", new ArrayList<>());
        }
        return "home";
    }
    
    @GetMapping("/ativos")
    public String listarAtivos(Model model) {
        List<AtivoEntity> ativos = ativoService.getAllAtivos();
        model.addAttribute("ativos", ativos);
        
        List<String> setores = ativos.stream()
                .map(AtivoEntity::getSetor)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("setores", setores);
        
        return "ativos";
    }
    
    @GetMapping("/carteira")
    public String minhaCarteira(Model model) {
        try {
            UsuarioEntity usuario = usuarioService.getById(1L);
            CarteiraEntity carteira = carteiraService.getCarteiraById(1L);
            
            List<PosicaoEntity> posicoes = carteira.getPosicoes();
            BigDecimal totalInvestido = carteira.getTotalInvestido();
            BigDecimal patrimonioTotal = usuario.getSaldoDisponivel().add(totalInvestido);
            BigDecimal rentabilidade = BigDecimal.ZERO; // Por enquanto zero, depois calculamos
            
            model.addAttribute("saldo", usuario.getSaldoDisponivel());
            model.addAttribute("posicoes", posicoes);
            model.addAttribute("totalInvestido", totalInvestido);
            model.addAttribute("patrimonioTotal", patrimonioTotal);
            model.addAttribute("rentabilidade", rentabilidade);
            
        } catch (RuntimeException e) {
            model.addAttribute("saldo", 0);
            model.addAttribute("posicoes", new ArrayList<>());
            model.addAttribute("totalInvestido", 0);
            model.addAttribute("patrimonioTotal", 0);
            model.addAttribute("rentabilidade", 0);
        }
        
        return "carteira";
    }
    
    @GetMapping("/transacoes/comprar")
    public String comprarAcoes(Model model) {
        model.addAttribute("ativos", ativoService.getAllAtivos());
        
        // Para mostrar o saldo
        try {
            UsuarioEntity usuario = usuarioService.getById(1L);
            model.addAttribute("saldo", usuario.getSaldoDisponivel());
        } catch (RuntimeException e) {
            model.addAttribute("saldo", 0);
        }
        
        return "comprar";
    }
}

