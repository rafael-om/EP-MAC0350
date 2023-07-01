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
public class OferecimentoDisciplinaDocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/oferecimento_disciplina_docente")
    public String formOferecimentoDisciplinaDocente(Model model) {
        model.addAttribute("oferecimento_disciplina_docente", new OferecimentoDisciplinaDocente());
        return "oferecimento_disciplina_docente";
    }

    @GetMapping("/oferecimento_disciplina_docente/{id_oferecimento_dd}")
    public String editOferecimentoDisciplinaDocente(Model model, @PathVariable Integer id_oferecimento_dd) {
        OferecimentoDisciplinaDocente oferecimento_disciplina_docente = jdbcTemplate.queryForObject("SELECT * FROM oferecimento_disciplina_docente WHERE id_oferecimento_dd = ?", 
            new OferecimentoDisciplinaDocenteRowMapper(), id_oferecimento_dd);
        model.addAttribute("oferecimento_disciplina_docente", oferecimento_disciplina_docente);
        return "oferecimento_disciplina_docente";
    }

    @PostMapping("/oferecimento_disciplina_docente")
    public String submitOferecimentoDisciplinaDocente(@ModelAttribute OferecimentoDisciplinaDocente oferecimento_disciplina_docente, Model model) {
        
        if (oferecimento_disciplina_docente.getId_oferecimento_dd() > 0) {
            jdbcTemplate.update(
                "UPDATE oferecimento_disciplina_docente SET codigo_oferecimento = ?, id_disciplina = ?, id_docente = ?, data_inicio = ?, data_fim = ?  WHERE id_oferecimento_dd = ?;",
                oferecimento_disciplina_docente.getCodigo_oferecimento(),
                oferecimento_disciplina_docente.getId_disciplina(),
                oferecimento_disciplina_docente.getId_docente(),
                oferecimento_disciplina_docente.getData_inicio(),
                oferecimento_disciplina_docente.getData_fim()
            );
        } else {
            jdbcTemplate.update(
                "INSERT INTO oferecimento_disciplina_docente (codigo_oferecimento, id_disciplina, id_docente, data_inicio, data_fim) VALUES (?,?,?,?,?);",
                oferecimento_disciplina_docente.getCodigo_oferecimento(),
                oferecimento_disciplina_docente.getId_disciplina(),
                oferecimento_disciplina_docente.getId_docente(),
                oferecimento_disciplina_docente.getData_inicio(),
                oferecimento_disciplina_docente.getData_fim());        
        }
        return "redirect:/pessoas";
    }

    @DeleteMapping("/oferecimento_disciplina_docente/{id_oferecimento_dd}")
    public String deleteOferecimentoDisciplinaDocente(@PathVariable Integer id_oferecimento_dd) {
        jdbcTemplate.update(
            "DELETE FROM oferecimento_disciplina_docente WHERE id_oferecimento_dd = ?;",
            id_oferecimento_dd
        );
        return "redirect:/pessoas";
    }
}