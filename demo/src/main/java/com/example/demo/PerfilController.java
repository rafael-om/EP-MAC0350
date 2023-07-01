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
public class PerfilController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/perfil")
    public String formPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "perfil";
    }

    @GetMapping("/perfil/{id_perfil}")
    public String editPerfil(Model model, @PathVariable Integer id_perfil) {
        Perfil perfil = jdbcTemplate.queryForObject("SELECT * FROM perfil WHERE id_perfil = ?", 
            new PerfilRowMapper(), id_perfil);
        model.addAttribute("perfil", perfil);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String submitPerfil(@ModelAttribute Perfil perfil, Model model) {
        
        if (perfil.getId_perfil() > 0) {
            jdbcTemplate.update(
                "UPDATE perfil SET codigo_perfil = ?, tipo_perfil = ? WHERE id_perfil = ?;",
                perfil.getCodigo_perfil(),
                perfil.getTipo_perfil(),
                perfil.getId_perfil()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO perfil (codigo_perfil, tipo_perfil) VALUES (?,?);",
                perfil.getCodigo_perfil(),
                perfil.getTipo_perfil());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/perfil/{id_perfil}")
    public String deletePerfil(@PathVariable Integer id_perfil) {
        jdbcTemplate.update(
            "DELETE FROM perfil WHERE id_perfil = ?;",
            id_perfil
        );
        return "redirect:/pessoas";
    }
}