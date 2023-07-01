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
public class DisciplinaController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/disciplina")
    public String formDisciplina(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina";
    }

    @GetMapping("/disciplina/{id_disciplina}")
    public String editDisciplina(Model model, @PathVariable Integer id_disciplina) {
        Disciplina disciplina = jdbcTemplate.queryForObject("SELECT * FROM disciplina WHERE id_disciplina = ?", 
            new DisciplinaRowMapper(), id_disciplina);
        model.addAttribute("disciplina", disciplina);
        return "disciplina";
    }

    @PostMapping("/disciplina")
    public String submitDisciplina(@ModelAttribute Disciplina disciplina, Model model) {
        
        if (disciplina.getId_disciplina() > 0) {
            jdbcTemplate.update(
                "UPDATE disciplina SET codigo_disciplina = ?, nome = ?, ementa = ?, data_criacao = ? WHERE id_disciplina = ?;",
                disciplina.getCodigo_disciplina(),
                disciplina.getNome(),
                disciplina.getEmenta(),
                disciplina.getData_criacao()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO disciplina (codigo_disciplina, nome, ementa, data_criacao) VALUES (?,?,?,?);",
                disciplina.getCodigo_disciplina(),
                disciplina.getNome(),
                disciplina.getEmenta(),
                disciplina.getData_criacao());        
        }
        return "redirect:/disciplinas";
    }

    @DeleteMapping("/disciplina/{id_disciplina}")
    public String deleteDisciplina(@PathVariable Integer id_disciplina) {
        jdbcTemplate.update(
            "DELETE FROM disciplina WHERE id_disciplina = ?;",
            id_disciplina
        );
        return "redirect:/disciplinas";
    }
}