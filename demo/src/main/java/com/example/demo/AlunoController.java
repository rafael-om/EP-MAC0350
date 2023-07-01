package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

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
public class AlunoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/aluno")
    public String formPessoa(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno";
    }

    @GetMapping("/aluno/{id_aluno}")
    public String editPessoa(Model model, @PathVariable Integer id_aluno) {
        Aluno aluno = jdbcTemplate.queryForObject("SELECT * FROM alunos WHERE id_aluno = ?", 
            new AlunoRowMapper(), id_aluno);
        model.addAttribute("aluno", aluno);
        return "aluno";
    }

    @PostMapping("/aluno")
    public String submitPessoa(@ModelAttribute Aluno aluno, Model model) {
        
        if (aluno.getId_aluno() > 0) {
            jdbcTemplate.update(
                "UPDATE aluno SET id_pessoa = ?, nota_ingresso = ?, curso = ? WHERE id_aluno = ?;",
                aluno.getId_pessoa(),
                aluno.getNota_ingresso(),
                aluno.getCurso(),
                aluno.getId_aluno()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO aluno (id_pessoa,nota_ingresso,curso) VALUES (?,?,?);",
                aluno.getId_pessoa(),
                aluno.getNota_ingresso(),
                aluno.getCurso()
            );
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/aluno/{id_aluno}")
    public String deletePessoa(@PathVariable Integer id_aluno) {
        jdbcTemplate.update(
            "DELETE FROM aluno WHERE id_aluno = ?;",
            id_aluno
        );
        return "redirect:/pessoas";
    }
}