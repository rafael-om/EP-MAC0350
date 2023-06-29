package com.example.demo;
import java.sql.Date;

public class Docente {

    // Dados cadastrais
    Integer id_docente;
    Integer id_pessoa;
    String especialidades;
    String funcoes_tecnicas;

    public Docente() {
        this.id_docente = -1;
    }

    public Integer getId_docente() {
        return id_docente;
    }

    public void setId_docente(Integer id_docente) {
        this.id_docente = id_docente;
    }

    public Integer getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getFuncoes_tecnicas() {
        return funcoes_tecnicas;
    }

    public void setFuncoes_tecnicas(String funcoes_tecnicas) {
        this.funcoes_tecnicas = funcoes_tecnicas;
    }
}