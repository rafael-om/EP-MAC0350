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
public class FuncionarioController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/funcionario")
    public String formFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionario";
    }

    @GetMapping("/funcionario/{id_funcionario}")
    public String editFuncionario(Model model, @PathVariable Integer id_funcionario) {
        Funcionario funcionario = jdbcTemplate.queryForObject("SELECT * FROM funcionario WHERE id_funcionario = ?", 
            new FuncionarioRowMapper(), id_funcionario);
        model.addAttribute("funcionario", funcionario);
        return "funcionario";
    }

    @PostMapping("/funcionario")
    public String submitFuncionario(@ModelAttribute Funcionario funcionario, Model model) {
        
        if (funcionario.getId_funcionario() > 0) {
            jdbcTemplate.update(
                "UPDATE funcionario SET id_pessoa = ?, especialidades = ?, funcoes_tecnicas = ? WHERE id_funcionario = ?;",
                funcionario.getId_pessoa(),
                funcionario.getEspecialidades(),
                funcionario.getFuncoes_tecnicas(),
                funcionario.getId_funcionario()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO funcionario (id_pessoa, especialidades, funcoes_tecnicas) VALUES (?,?,?);",
                funcionario.getId_pessoa(),
                funcionario.getEspecialidades(),
                funcionario.getFuncoes_tecnicas());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/funcionario/{id_funcionario}")
    public String deleteFuncionario(@PathVariable Integer id_funcionario) {
        jdbcTemplate.update(
            "DELETE FROM funcionario WHERE id_funcionario = ?;",
            id_funcionario
        );
        return "redirect:/pessoas";
    }
}