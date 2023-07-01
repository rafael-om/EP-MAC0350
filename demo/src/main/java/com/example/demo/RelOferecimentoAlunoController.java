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
public class RelOferecimentoAlunoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/rel_oferecimento_aluno")
    public String formRelOferecimentoAluno(Model model) {
        model.addAttribute("rel_oferecimento_aluno", new RelOferecimentoAluno());
        return "rel_oferecimento_aluno";
    }

    @GetMapping("/rel_oferecimento_aluno/{id_rel_oferecimento_aluno}")
    public String editRelOferecimentoAluno(Model model, @PathVariable Integer id_oferecimento_aluno) {
        RelOferecimentoAluno rel_oferecimento_aluno = jdbcTemplate.queryForObject("SELECT * FROM rel_oferecimento_aluno WHERE id_oferecimento_aluno = ?", 
            new RelOferecimentoAlunoRowMapper(), id_oferecimento_aluno);
        model.addAttribute("rel_oferecimento_aluno", rel_oferecimento_aluno);
        return "rel_oferecimento_aluno";
    }

    @PostMapping("/rel_oferecimento_aluno")
    public String submitRelOferecimentoAluno(@ModelAttribute RelOferecimentoAluno rel_oferecimento_aluno, Model model) {
        
        if (rel_oferecimento_aluno.getId_oferecimento_aluno() > 0) {
            jdbcTemplate.update(
                "UPDATE rel_oferecimento_aluno SET id_oferecimento_dd = ?, id_aluno = ?, nota = ? WHERE id_oferecimento_aluno = ?;",
                rel_oferecimento_aluno.getId_oferecimento_dd(),
                rel_oferecimento_aluno.getId_aluno(),
                rel_oferecimento_aluno.getNota(),
                rel_oferecimento_aluno.getId_oferecimento_aluno()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO rel_oferecimento_aluno (id_oferecimento_dd, id_aluno, nota) VALUES (?,?,?);",
                rel_oferecimento_aluno.getId_oferecimento_dd(),
                rel_oferecimento_aluno.getId_aluno(),
                rel_oferecimento_aluno.getNota());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/rel_oferecimento_aluno/{id_oferecimento_aluno}")
    public String deleteRelOferecimentoAluno(@PathVariable Integer id_oferecimento_aluno) {
        jdbcTemplate.update(
            "DELETE FROM rel_oferecimento_aluno WHERE id_oferecimento_aluno = ?;",
            id_oferecimento_aluno
        );
        return "redirect:/pessoas";
    }
}