package com.example.demo;
import java.sql.Date;

public class RelOferecimentoAluno {

    // Dados cadastrais
    Integer id_oferecimento_aluno;
    Integer id_oferecimento_dd;
    Integer id_aluno;
    Double nota;

    public RelOferecimentoAluno() {
        this.id_oferecimento_aluno = -1;
    }

    public Integer getId_oferecimento_aluno() {
        return id_oferecimento_aluno;
    }

    public void setId_oferecimento_aluno(Integer id_oferecimento_aluno) {
        this.id_oferecimento_aluno = id_oferecimento_aluno;
    }

    public Integer getId_oferecimento_dd() {
        return id_oferecimento_dd;
    }

    public void setId_oferecimento_dd(Integer id_oferecimento_dd) {
        this.id_oferecimento_dd = id_oferecimento_dd;
    }

    public Integer getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Integer id_aluno) {
        this.id_aluno = id_aluno;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}