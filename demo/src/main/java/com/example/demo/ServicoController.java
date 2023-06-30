package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class ServicoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/servico")
    public String formServico(Model model) {
        model.addAttribute("servico", new Servico());
        return "servico";
    }

    @GetMapping("/servico/{id_servico}")
    public String editServico(Model model, @PathVariable Integer id_servico) {
        Servico servico = jdbcTemplate.queryForObject("SELECT * FROM servico WHERE id_servico = ?", 
            new ServicoRowMapper(), id_servico);
        model.addAttribute("servico", servico);
        return "servico";
    }

    @PostMapping("/servico")
    public String submitServico(@ModelAttribute Servico servico, Model model) {
        
        if (servico.getId_servico() > 0) {
            jdbcTemplate.update(
                "UPDATE servico SET codigo_servico = ?, id_perfil = ?, tipo_servico = ? WHERE id_servico = ?;",
                servico.getCodigo_servico(),
                servico.getId_perfil(),
                servico.getTipo_servico()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO servico (codigo_servico, id_perfil, tipo_servico) VALUES (?,?,?);",
                servico.getCodigo_servico(),
                servico.getId_perfil(),
                servico.getTipo_servico());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/servico/{id_servico}")
    public String deleteServico(@PathVariable Integer id_servico) {
        jdbcTemplate.update(
            "DELETE FROM servico WHERE id_servico = ?;",
            id_servico
        );
        return "redirect:/pessoas";
    }
}