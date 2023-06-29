package com.example.demo;
import java.sql.Date;

public class Funcionario {

    // Dados cadastrais
    Integer id_funcionario;
    Integer id_pessoa;
    String especialidades;
    String funcoes_tecnicas;

    public Funcionario() {
        this.id_funcionario = -1;
    }

    public Integer getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
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