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
public class DocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/docente")
    public String formDocente(Model model) {
        model.addAttribute("docente", new Docente());
        return "docente";
    }

    @GetMapping("/docente/{id_docente}")
    public String editDocente(Model model, @PathVariable Integer id_docente) {
        Docente docente = jdbcTemplate.queryForObject("SELECT * FROM docente WHERE id_docente = ?", 
            new DocenteRowMapper(), id_docente);
        model.addAttribute("docente", docente);
        return "docente";
    }

    @PostMapping("/docente")
    public String submitDocente(@ModelAttribute Docente docente, Model model) {
        
        if (docente.getId_docente() > 0) {
            jdbcTemplate.update(
                "UPDATE docente SET id_pessoa = ?, especialidades = ?, funcoes_tecnicas = ? WHERE id_docente = ?;",
                docente.getId_pessoa(),
                docente.getEspecialidades(),
                docente.getFuncoes_tecnicas(),
                docente.getId_docente()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO docente (id_pessoa, especialidades, funcoes_tecnicas) VALUES (?,?,?);",
                docente.getId_pessoa(),
                docente.getEspecialidades(),
                docente.getFuncoes_tecnicas());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/docente/{id_docente}")
    public String deleteDocente(@PathVariable Integer id_docente) {
        jdbcTemplate.update(
            "DELETE FROM docente WHERE id_docente = ?;",
            id_docente
        );
        return "redirect:/pessoas";
    }
}