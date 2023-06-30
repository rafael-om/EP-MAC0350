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
public class RelPessoaServicoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/rel_pessoa_servico")
    public String formRelPessoaServico(Model model) {
        model.addAttribute("rel_pessoa_servico", new RelPessoaServico());
        return "rel_pessoa_servico";
    }

    @GetMapping("/rel_pessoa_servico/{id_rel_pessoa_servico}")
    public String editRelPessoaServico(Model model, @PathVariable Integer id_pessoa_servico) {
        RelPessoaServico rel_pessoa_servico = jdbcTemplate.queryForObject("SELECT * FROM rel_pessoa_servico WHERE id_pessoa_servico = ?", 
            new RelPessoaServicoRowMapper(), id_pessoa_servico);
        model.addAttribute("rel_pessoa_servico", rel_pessoa_servico);
        return "rel_pessoa_servico";
    }

    @PostMapping("/rel_pessoa_servico")
    public String submitRelPessoaServico(@ModelAttribute RelPessoaServico rel_pessoa_servico, Model model) {
        
        if (rel_pessoa_servico.getId_rel_pessoa_servico() > 0) {
            jdbcTemplate.update(
                "UPDATE rel_pessoa_servico SET id_pessoa = ?, id_servico = ?, data_uso_servico = ? WHERE id_pessoa_servico = ?;",
                rel_pessoa_servico.getId_pessoa(),
                rel_pessoa_servico.getId_servico(),
                rel_pessoa_servico.getData_uso_servico()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO rel_pessoa_servico (id_pessoa, id_servico, data_uso_servico) VALUES (?,?,?);",
                rel_pessoa_servico.getId_pessoa(),
                rel_pessoa_servico.getId_servico(),
                rel_pessoa_servico.getData_uso_servico());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/rel_pessoa_servico/{id_pessoa_servico}")
    public String deleteRelPessoaServico(@PathVariable Integer id_pessoa_servico) {
        jdbcTemplate.update(
            "DELETE FROM rel_pessoa_servico WHERE id_pessoa_servico = ?;",
            id_pessoa_servico
        );
        return "redirect:/pessoas";
    }
}