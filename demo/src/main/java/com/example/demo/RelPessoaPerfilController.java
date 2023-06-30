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
public class RelPessoaPerfilController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/rel_pessoa_perfil")
    public String formRelPessoaPerfil(Model model) {
        model.addAttribute("rel_pessoa_perfil", new RelPessoaPerfil());
        return "rel_pessoa_perfil";
    }

    @GetMapping("/rel_pessoa_perfil/{id_pessoa_perfil}")
    public String editRelPessoaPerfil(Model model, @PathVariable Integer id_pessoa_perfil) {
        RelPessoaPerfil rel_pessoa_perfil = jdbcTemplate.queryForObject("SELECT * FROM rel_pessoa_perfil WHERE id_pessoa_perfil = ?", 
            new RelPessoaPerfilRowMapper(), id_pessoa_perfil);
        model.addAttribute("rel_pessoa_perfil", rel_pessoa_perfil);
        return "rel_pessoa_perfil";
    }

    @PostMapping("/rel_pessoa_perfil")
    public String submitRelPessoaPerfil(@ModelAttribute RelPessoaPerfil rel_pessoa_perfil, Model model) {
        
        if (rel_pessoa_perfil.getId_pessoa_perfil() > 0) {
            jdbcTemplate.update(
                "UPDATE rel_pessoa_perfil SET id_pessoa = ?, id_perfil = ? WHERE id_pessoa_perfil = ?;",
                rel_pessoa_perfil.getId_pessoa(),
                rel_pessoa_perfil.getId_perfil()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO rel_pessoa_perfil (id_pessoa, id_perfil) VALUES (?,?);",
                rel_pessoa_perfil.getId_pessoa(),
                rel_pessoa_perfil.getId_perfil());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/rel_pessoa_perfil/{id_pessoa_perfil}")
    public String deleteRelPessoaPerfil(@PathVariable Integer id_pessoa_perfil) {
        jdbcTemplate.update(
            "DELETE FROM rel_pessoa_perfil WHERE id_pessoa_perfil = ?;",
            id_pessoa_perfil
        );
        return "redirect:/pessoas";
    }
}