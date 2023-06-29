package com.example.demo;
import java.sql.Date;

public class Disciplina {

    // Dados cadastrais
    Integer id_disciplina;
    String codigo_disciplina;
    String nome;
    String ementa;
    Date data_criacao;

    public Disciplina() {
        this.id_disciplina = -1;
    }

    public Integer getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Integer id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getCodigo_disciplina() {
        return codigo_disciplina;
    }

    public void setCodigo_disciplina(String codigo_disciplina) {
        this.codigo_disciplina = codigo_disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }
}