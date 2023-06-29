package com.example.demo;
import java.sql.Date;

public class Aluno {

    // Dados cadastrais
    Integer id_aluno;
    Integer id_pessoa;
    Double nota_ingresso;
    String curso;

    public Aluno() {
        this.id_aluno = -1;
    }

    public Integer getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Integer id_aluno) {
        this.id_aluno = id_aluno;
    }

    public Integer getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Double getNota_ingresso() {
        return nota_ingresso;
    }

    public void setNota_ingresso(Double nota_ingresso) {
        this.nota_ingresso = nota_ingresso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}