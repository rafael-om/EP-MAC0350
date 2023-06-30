package com.example.demo;

import java.util.ArrayList;
import java.util.List;

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
public class PessoasController {

    @Autowired 
    JdbcTemplate jdbcTemplate;

    @GetMapping("/pessoas")
    public String fillTables(Model model) { // ORDER BY id_pessoa DESC
        List<Aluno> alunos = jdbcTemplate.query(
                "SELECT * FROM aluno",
                new AlunoRowMapper());
        model.addAttribute("listaAlunos", alunos);

        List<Pessoa> pessoas = jdbcTemplate.query(
                "SELECT * FROM pessoa",
                new PessoaRowMapper());
        model.addAttribute("listapessoas", pessoas);

        List<Disciplina> disciplinas = jdbcTemplate.query(
                "SELECT * FROM disciplina",
                new DisciplinaRowMapper());
        model.addAttribute("listaDisciplinas", disciplinas);

        List<Docente> docentes = jdbcTemplate.query(
                "SELECT * FROM docente",
                new DocenteRowMapper());
        model.addAttribute("listaDocentes", docentes);

        List<Funcionario> funcionarios = jdbcTemplate.query(
                "SELECT * FROM funcionario",
                new FuncionarioRowMapper());
        model.addAttribute("listaFuncionarios", funcionarios);

        List<OferecimentoDisciplinaDocente> oferecimentos = jdbcTemplate.query(
                "SELECT * FROM oferecimento_disciplina_docente",
                new OferecimentoDisciplinaDocenteRowMapper());
        model.addAttribute("listaOferecimentos", oferecimentos);

        return "pessoas";
    }

}