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
public class PessoaController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/pessoa")
    public String formPessoa(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "pessoa";
    }

    @GetMapping("/pessoa/{id_pessoa}")
    public String editPessoa(Model model, @PathVariable Integer id_pessoa) {
        Pessoa pessoa = jdbcTemplate.queryForObject("SELECT * FROM pessoa WHERE id_pessoa = ?", 
            new PessoaRowMapper(), id_pessoa);
        model.addAttribute("pessoa", pessoa);
        return "pessoa";
    }

    @PostMapping("/pessoa")
    public String submitPessoa(@ModelAttribute Pessoa pessoa, Model model) {
        
        if (pessoa.getId_pessoa() > 0) {
            jdbcTemplate.update(
                "UPDATE pessoa SET cpf = ?, nome = ?, endereco = ?, instituicao = ?, data_nascimento = ?, login_pessoa = ?, senha = ? WHERE id_pessoa = ?;",
                new BigInteger(pessoa.getCpf()),
                pessoa.getNome(),
                pessoa.getEndereco(),
                pessoa.getInstituicao(),
                pessoa.getData_nascimento(),
                pessoa.getLogin_pessoa(),
                pessoa.getSenha(),
                pessoa.getId_pessoa()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO pessoa (cpf, nome, endereco,instituicao,data_nascimento,login_pessoa,senha) VALUES (?,?,?,?,?,?,?);",
                new BigInteger(pessoa.getCpf()),
                pessoa.getNome(),
                pessoa.getEndereco(),
                pessoa.getInstituicao(),
                pessoa.getData_nascimento(),
                pessoa.getLogin_pessoa(),
                pessoa.getSenha());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/pessoa/{id_pessoa}")
    public String deletePessoa(@PathVariable Integer id_pessoa) {
        jdbcTemplate.update(
            "DELETE FROM pessoa WHERE id_pessoa = ?;",
            id_pessoa
        );
        return "redirect:/pessoas";
    }
}